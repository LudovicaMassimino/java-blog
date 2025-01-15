package it.ludo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.ludo.model.Article;
import it.ludo.repository.ArticleRepo;
import it.ludo.repository.CategoryRepo;
import it.ludo.repository.UserRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping
public class ArticleController {

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/home")
    public String getArticles(Model model) {
        List<Article> articles = articleRepo.findAll();
        model.addAttribute("list", articles);

        return "home/index";
    }

    @GetMapping("/home/article/{id}")
    public String readMore(@PathVariable("id") Integer id, Model model) {
        Article article = articleRepo.getReferenceById(id);
        model.addAttribute("article", article);
        return "home/article-detail";
    }

}
