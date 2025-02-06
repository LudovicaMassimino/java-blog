package it.ludo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.ludo.model.Article;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
 
    @Query("SELECT t FROM Article t WHERE t.category.name = :categoryName")
    List<Article> findByCategoryName(@Param("categoryName") String categoryName);

    List<Article> findByTitleContainingIgnoreCase(String title);

    List<Article> findByBodyContainingIgnoreCase(String body);

    List<Article> findByAuthorUsername(String username);

    List<Article> findByAuthorUsernameAndBodyContainingIgnoreCase(String username, String body);

    List<Article> findByAuthorUsernameAndTitleContainingIgnoreCase(String username, String title);

    List<Article> findByAuthorUsernameAndCategoryName(String username, String category);
}
