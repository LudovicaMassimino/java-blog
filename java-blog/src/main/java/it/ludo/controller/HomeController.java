package it.ludo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.ludo.model.Article;
import it.ludo.model.Article.Status;
import it.ludo.model.User;
import it.ludo.repository.ArticleRepo;
import it.ludo.repository.CategoryRepo;
import it.ludo.repository.UserRepo;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/home")
    public String home(@RequestParam(name = "category", required = false) String category, Model model,
            Principal principal) {

        // Verifica che il Principal non sia null
        if (principal != null) {
            String username = principal.getName();
            Optional<User> loggedUserOpt = userRepo.findByUsername(username);
            if (loggedUserOpt.isPresent()) {
                User loggedUser = loggedUserOpt.get();
                model.addAttribute("user", loggedUser);
                model.addAttribute("loggedUser", username);
            }
        }

        List<Article> articles;

        if (category == null || category.isEmpty()) {
            articles = articleRepo.findByStatus(Status.APPROVED); // Mostra tutti gli articoli approvati
        } else {
            articles = articleRepo.findByCategoryName(category).stream()
                    .filter(article -> article.getStatus() == Status.APPROVED)
                    .collect(Collectors.toList());
        }

        boolean noArticles = articles.isEmpty();

        model.addAttribute("list", articles);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("noArticles", noArticles);

        return "home/index";
    }

    @GetMapping("/home/article/{id}")
    public String readMore(@PathVariable("id") Integer id,
            @RequestHeader(value = "Referer", required = false) String referer, Model model, Principal principal) {
        Article article = articleRepo.getReferenceById(id);
        model.addAttribute("article", article);

        // Verifica che il Principal non sia null
        if (principal != null) {
            String username = principal.getName();
            Optional<User> loggedUserOpt = userRepo.findByUsername(username);
            if (loggedUserOpt.isPresent()) {
                User loggedUser = loggedUserOpt.get();
                model.addAttribute("user", loggedUser);
                model.addAttribute("loggedUser", username);
            }
        }

        // Determina la pagina di origine
        if (referer != null && (referer.contains("/home") || referer.contains("/dashboard/admin"))) {
            model.addAttribute("previousPage", referer);
        } else {
            model.addAttribute("previousPage", "/home"); // Default fallback
        }
        return "home/article-detail";
    }
}
