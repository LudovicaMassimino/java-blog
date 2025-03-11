package it.ludo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ludo.exception.ArticleNotFoundException;
import it.ludo.model.Article;
import it.ludo.repository.ArticleRepo;


@Service
public class ArticleServiceImplements  implements ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImplements.class);

    @Override
    public List<Article> getApprovedArticles() {
        return articleRepo.findByStatus(Article.Status.APPROVED);
    }

    @Override
    public List<Article> getApprovedArticlesByCategory(String category) {
        return articleRepo.findByCategoryName(category)
                          .stream()
                          .filter(article -> article.getStatus() == Article.Status.APPROVED)
                          .collect(Collectors.toList());
    }

    @Override
    public List<Article> getArticlesByCategory(String category) {
        return articleRepo.findByCategoryName(category);
    }

    @Override
    public List<Article> getArticlesByAuthorAndCategory(String username, String category) {
        return articleRepo.findByAuthorUsernameAndCategoryName(username, category);
    }

    @Override
    public List<Article> getArticlesByAuthorAndTitle(String username, String title) {
        return articleRepo.findByAuthorUsernameAndTitleContainingIgnoreCase(username, title);
    }

    @Override
    public List<Article> getArticlesByAuthorAndBody(String username, String body) {
        return articleRepo.findByAuthorUsernameAndBodyContainingIgnoreCase(username, body);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepo.findAll();
    }

    @Override
    public List<Article> getArticlesByAuthor(String username) {
        return articleRepo.findByAuthorUsername(username);
    }

    @Override
    public List<Article> getArticlesByBody(String body) {
        return articleRepo.findByBodyContainingIgnoreCase(body);
    }

    @Override
    public List<Article> getArticlesByTitle(String title) {
        return articleRepo.findByTitleContainingIgnoreCase(title);
    }
    
    @Override
    public Article getArticleById(Integer id) {
        return articleRepo.findById(id)
                          .orElseThrow(() -> new ArticleNotFoundException("Articolo non trovato con id: " + id));
    }

    @Override
    public Article saveArticle(Article article) {
        logger.info("Salvataggio articolo: {}", article.getTitle());
        return articleRepo.save(article);
    }

    @Override
    public void deleteArticle(Integer id) {
        if (!articleRepo.existsById(id)) {
            throw new ArticleNotFoundException("Impossibile eliminare: articolo non trovato con id: " + id);
        }
        articleRepo.deleteById(id);
        logger.info("Articolo eliminato con successo, ID: {}", id);
    }

    @Override
    public Article approveArticle(Integer id) {
        Article article = getArticleById(id);
        article.setStatus(Article.Status.APPROVED);
        logger.info("Articolo approvato con successo, ID: {}", id);
        return articleRepo.save(article);
    }

    @Override
    public Article rejectArticle(Integer id) {
        Article article = getArticleById(id);
        article.setStatus(Article.Status.REJECTED);
        logger.info("Articolo rifiutato con successo, ID: {}", id);
        return articleRepo.save(article);
    }
}