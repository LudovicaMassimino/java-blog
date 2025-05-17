package it.ludo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ludo.exception.ArticleNotFoundException;
import it.ludo.mapper.ArticleMapper;
import it.ludo.model.Article;
import it.ludo.model.ArticleDTO;
import it.ludo.model.Category;
import it.ludo.model.User;
import it.ludo.repository.CategoryRepo;
import it.ludo.repository.UserRepo;
import it.ludo.response.Payload;
import it.ludo.service.ArticleService;

@RestController
@CrossOrigin
@RequestMapping("/api/articles")

public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping
    public ResponseEntity<Payload<List<Article>>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(new Payload<>(articles, null, HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payload<Article>> getArticleById(@PathVariable Integer id) {
        try {
            Article article = articleService.getArticleById(id);
            return ResponseEntity.ok(new Payload<>(article, null, HttpStatus.OK));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Payload<>(null, "Articolo non trovato", HttpStatus.NOT_FOUND));
        }
    }

    // uso saveArticle gestisce sia la creazione che l'aggionamento
    // dell'articolo, quindi non serve un metodo separato
    @PostMapping
    public ResponseEntity<Payload<Article>> createArticle(@RequestBody Article article) {
        try {
            article.setId(null); // ID nullo per forzare la creazione
            Article created = articleService.saveArticle(article);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Payload<>(created, null, HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Payload<>(null, "Errore durante la creazione dell'articolo",
                            HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payload<ArticleDTO>> updateArticle(@PathVariable Integer id, @RequestBody ArticleDTO dto) {
        try {
            Article existing = articleService.getArticleById(id); // per validare che esista

            User author = userRepo.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("User non trovato"));
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category non trovata"));

            // mappa il DTO ad Entity
            Article articleToUpdate = ArticleMapper.toEntity(dto, author, category);
            articleToUpdate.setId(existing.getId()); // sicuro!

            Article updated = articleService.saveArticle(articleToUpdate);

            // ritorna DTO aggiornato
            ArticleDTO responseDto = ArticleMapper.toDto(updated);
            return ResponseEntity.ok(new Payload<>(responseDto, null, HttpStatus.OK));

        } catch (ArticleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Payload<>(null, "Articolo non trovato per l'aggiornamento", HttpStatus.NOT_FOUND));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Payload<Void>> deleteArticle(@PathVariable Integer id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.ok(new Payload<>(null, null, HttpStatus.NO_CONTENT));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Payload<>(null, "Articolo da eliminare non trovato", HttpStatus.NOT_FOUND));
        }
    }
}
