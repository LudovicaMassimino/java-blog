package it.ludo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArticleDTO {

    private Integer id;
    private String title;
    private String body;
    private LocalDate articleDate;
    private LocalDateTime createdAt;
    private String image;
    private Integer authorId;   // solo ID, non l'oggetto User
    private Integer categoryId; // solo ID, non l'oggetto Category
    private Article.Status status;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public LocalDate getArticleDate() {
        return articleDate;
    }
    public void setArticleDate(LocalDate articleDate) {
        this.articleDate = articleDate;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Integer getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public Article.Status getStatus() {
        return status;
    }
    public void setStatus(Article.Status status) {
        this.status = status;
    }

    // Getters e Setters
    
}
