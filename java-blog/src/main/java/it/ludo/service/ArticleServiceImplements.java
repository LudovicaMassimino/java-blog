package it.ludo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ludo.model.Article;
import it.ludo.repository.ArticleRepo;

@Service
public class ArticleServiceImplements  implements ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    @Override
    public List<Article> getApprovedArticles() {
        return articleRepo.findByStatus(Article.Status.APPROVED);
    }

    @Override
    public List<Article> getArticlesByCategory(String category) {
        return articleRepo.findByCategoryName(category)
                          .stream()
                          .filter(article -> article.getStatus() == Article.Status.APPROVED)
                          .collect(Collectors.toList());
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleRepo.findById(id)
                          .orElseThrow(() -> new RuntimeException("Articolo non trovato con id: " + id));
    }

    @Override
    public Article saveArticle(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public void deleteArticle(Integer id) {
        articleRepo.deleteById(id);
    }

    @Override
    public Article approveArticle(Integer id) {
        Article article = getArticleById(id);
        article.setStatus(Article.Status.APPROVED);
        return articleRepo.save(article);
    }

    @Override
    public Article rejectArticle(Integer id) {
        Article article = getArticleById(id);
        article.setStatus(Article.Status.REJECTED);
        return articleRepo.save(article);
    }
}
