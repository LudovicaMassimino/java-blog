package it.ludo.service;

import java.util.List;
import java.util.Optional;
import it.ludo.model.Article;

public interface ArticleService {

    List<Article> getApprovedArticles();
    List<Article> getArticlesByCategory(String category);
    Article getArticleById(Integer id);
    Article saveArticle(Article article);
    void deleteArticle(Integer id);
    Article approveArticle(Integer id);
    Article rejectArticle(Integer id);
}
