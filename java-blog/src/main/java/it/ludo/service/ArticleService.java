package it.ludo.service;

import java.util.List;
import it.ludo.model.Article;

public interface ArticleService {

    List<Article> getApprovedArticles();
    List<Article> getApprovedArticlesByCategory(String category);
    List<Article> getArticlesByCategory(String category);
    List<Article> getArticlesByAuthorAndCategory(String username, String category);
    Article getArticleById(Integer id);
    Article saveArticle(Article article);
    void deleteArticle(Integer id);
    Article approveArticle(Integer id);
    Article rejectArticle(Integer id);
    List<Article> getAllArticles();
    List<Article> getArticlesByAuthor(String username);
}
