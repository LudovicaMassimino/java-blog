package it.ludo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.ludo.model.Article;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {

    List<Article> findByCategoryNameOrderByCreatedAtDesc(@Param("categoryName") String categoryName);

    List<Article> findAllByOrderByCreatedAtDesc();
    List<Article> findByAuthorUsernameOrderByCreatedAtDesc(String username);

    List<Article> findByAuthorUsernameAndCategoryName(String username, String category);

    List<Article> findByStatus(Article.Status status);

    List<Article> findByStatusOrderByCreatedAtDesc(Article.Status status);
}
