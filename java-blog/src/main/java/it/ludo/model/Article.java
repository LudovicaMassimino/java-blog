package it.ludo.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ARTICLE")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Campo Obbligatorio")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Campo Obbligatorio")
    @Column(nullable = false)
    private String body;

    // @NotNull(message = "Campo Obbligatorio")
    @Column(nullable = false)
    private LocalDateTime article_date;

    @Column(nullable = false)
    private String image;

    // @NotNull(message = "Campo Obbligatorio")
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    // @NotNull(message = "Campo Obbligatorio")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

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

    public LocalDateTime getArticle_date() {
        return article_date;
    }

    public void setArticle_date(LocalDateTime article_date) {
        this.article_date = article_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
