package it.ludo.mapper;

import it.ludo.model.Article;
import it.ludo.model.ArticleDTO;
import it.ludo.model.Category;
import it.ludo.model.User;

public class ArticleMapper {

    public static Article toEntity(ArticleDTO dto, User author, Category category) {
        Article article = new Article();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setBody(dto.getBody());
        article.setArticleDate(dto.getArticleDate());
        article.setCreatedAt(dto.getCreatedAt());
        article.setImage(dto.getImage());
        article.setAuthor(author);         // da User esistente
        article.setCategory(category);     // da Category esistente
        article.setStatus(dto.getStatus());
        return article;
    }

    public static ArticleDTO toDto(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setBody(article.getBody());
        dto.setArticleDate(article.getArticleDate());
        dto.setCreatedAt(article.getCreatedAt());
        dto.setImage(article.getImage());
        dto.setAuthorId(article.getAuthor() != null ? article.getAuthor().getId() : null);
        dto.setCategoryId(article.getCategory() != null ? article.getCategory().getId() : null);
        dto.setStatus(article.getStatus());
        return dto;
    }
}
