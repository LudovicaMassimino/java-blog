package it.ludo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.ludo.model.Article;
import it.ludo.model.Category;
import it.ludo.model.User;
import it.ludo.repository.CategoryRepo;
import it.ludo.repository.UserRepo;
import it.ludo.service.ArticleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping
public class DashboardController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/dashboard/admin")
    public String index(Model model, @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "body", required = false) String body,
            @RequestParam(name = "category", required = false) String category, Principal principal) {

        if (principal == null) {
            return "redirect:/home";
        }

        String username = principal.getName();
        Optional<User> loggedUserOpt = userRepo.findByUsername(username);
        User loggedUser = loggedUserOpt.get();

        // Utente loggato

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<Article> articles = new ArrayList<>();

        if (isAdmin) {
            articles = articleService.getAllArticles();
        } else {
            articles = articleService.getArticlesByAuthor(username);
        }

        // Gestione del filtro per categoria
        if (category != null && !category.isEmpty()) {
            if (isAdmin) {
                articles = articleService.getArticlesByCategory(category);
            } else {
                articles = articleService.getArticlesByAuthorAndCategory(username, category);
            }
        }

        boolean noArticles = articles.isEmpty(); // se non ci sono art per quella cat

        // Recupera tutte le categorie ordinate
        List<Category> categories = categoryRepo.findAllByOrderByNameAsc();

        model.addAttribute("list", articles);
        model.addAttribute("category", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("noArticles", noArticles);
        model.addAttribute("user", loggedUser);
        model.addAttribute("loggedUser", username);
        model.addAttribute("isAdmin", isAdmin);

        return "dashboard/admin_dash";
    }

    @PostMapping("/dashboard/admin")
    public String addCategory(@RequestParam(name = "newCategory", required = false) String newCategory,
            RedirectAttributes redirectAttributes) {

        if (newCategory != null && !newCategory.isEmpty()) {
            List<String> AllAllowedLanguages = Arrays.asList(
                    "Java", "Python", "C++", "C", "JavaScript", "Ruby", "PHP", "Go", "Swift", "C#", "TypeScript",
                    "Kotlin", "Rust",
                    "Dart", "R", "Objective-C", "Perl", "Lua", "Haskell", "Erlang", "Elixir", "F#", "Lisp", "Clojure",
                    "OCaml", "Scala",
                    "Julia", "MATLAB", "SQL", "PL/SQL", "T-SQL", "GraphQL", "Ada", "Embedded C", "Assembly",
                    "PowerShell", "Fortran",
                    "IDL", "Wolfram Language", "Solidity", "Vyper", "Move", "COBOL", "Prolog", "Brainfuck", "Malbolge",
                    "Whitespace",
                    "Befunge", "Piet");

            // Verifica se il linguaggio inserito è tra quelli ammessi (case-insensitive)
            boolean isAllowed = AllAllowedLanguages.stream()
                    .anyMatch(lang -> lang.equalsIgnoreCase(newCategory.trim()));

            if (!isAllowed) {
                // Se il valore non è un linguaggio ammesso, imposta il messaggio d'errore e
                // reindirizza
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Invalid category!<br>Please enter a programming language");
                return "redirect:/dashboard/admin";
            }
            // Controlla se la categoria esiste già
            Category existingCategory = categoryRepo.findByName(newCategory);
            if (existingCategory == null) {
                // Se non esiste, crea una nuova categoria
                Category newCat = new Category();
                newCat.setName(newCategory);
                categoryRepo.save(newCat); // Salva la nuova categoria nel database
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "This category already exists!<br>Please enter another category name");
                return "redirect:/dashboard/admin";
            }
        }

        return "redirect:/dashboard/admin";
    }
}