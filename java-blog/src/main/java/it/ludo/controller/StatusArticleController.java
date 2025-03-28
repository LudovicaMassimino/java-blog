package it.ludo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.ludo.model.Article;
import it.ludo.model.Article.Status;
import it.ludo.service.ArticleService;

@Controller
@RequestMapping
public class StatusArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/articles/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveArticle(@PathVariable("id") Integer id) {

        Article article = articleService.getArticleById(id);
        article.setStatus(Status.APPROVED);
        articleService.saveArticle(article);

        return "redirect:/dashboard/admin";
    }

    @PostMapping("/articles/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public String rejectArticle(@PathVariable("id") Integer id) {

        Article article = articleService.getArticleById(id);
        article.setStatus(Status.REJECTED);
        articleService.saveArticle(article);

        return "redirect:/dashboard/admin";
    }
}