package it.ludo.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.ludo.exception.ArticleNotFoundException;
import it.ludo.model.Article;
import it.ludo.model.Article.Status;
import it.ludo.model.User;
import it.ludo.repository.CategoryRepo;
import it.ludo.repository.UserRepo;
import it.ludo.service.ArticleService;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class CrudArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/article/{id}")
    public String show(@PathVariable("id") Integer id,
            @RequestHeader(value = "Referer", required = false) String referer, Model model, Principal principal) {

        String username = principal.getName();
        Optional<User> loggedUserOpt = userRepo.findByUsername(username);
        User loggedUser = loggedUserOpt.get();

        model.addAttribute("user", loggedUser);
        model.addAttribute("loggedUser", username);

        try {
            Article article = articleService.getArticleById(id);  // Usa ArticleService
            model.addAttribute("article", article);
        } catch (ArticleNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/404"; // O una vista adeguata per l'errore
        }

        // Determina la pagina di origine
        if (referer != null && (referer.contains("/home") || referer.contains("/dashboard/admin"))) {
            model.addAttribute("previousPage", referer);
        } else {
            model.addAttribute("previousPage", "/home"); // Default fallback
        }
        return "home/article-detail";
    }

    @GetMapping("/dashboard/create")
    public String create(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/home";
        }

        String username = principal.getName();
        Optional<User> loggedUserOpt = userRepo.findByUsername(username);
        User loggedUser = loggedUserOpt.get();

        // Utente loggato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("article", new Article());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("user", loggedUser);
        model.addAttribute("loggedUser", username);

        return "/dashboard/dash_create";
    }

    @PostMapping("/dashboard/create")
    public String create(@Valid @ModelAttribute("article") Article articleForm,
            BindingResult bindingResult,
            Model model, Principal principal, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepo.findAll());
            return "/dashboard/dash_create";
        }

        if (articleForm.getArticle_date() == null) {
            articleForm.setArticle_date(LocalDate.now());
        }

        // Recupera il nome dell'utente loggato (sia che sia admin o user)
        String username = principal.getName();
        Optional<User> loggedUserOpt = userRepo.findByUsername(username);
        if (loggedUserOpt.isPresent()) {
            articleForm.setAuthor(loggedUserOpt.get());
        } else {
            throw new RuntimeException("Utente non trovato per username: " + username);
        }

        // Imposta lo status a IN_REVIEW per la nuova creazione
        articleForm.setStatus(Status.IN_REVIEW);

        MultipartFile imageFile = articleForm.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Usa un percorso stabile
                String uploadDir = System.getProperty("user.home") + "/uploads/";

                // Crea la directory se non esiste
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                // Salva il file
                String fileName = imageFile.getOriginalFilename();
                File file = new File(uploadDir + fileName);
                imageFile.transferTo(file);

                // Salva il nome del file nella proprietà `image`
                articleForm.setImage("uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Image upload error.");
                return "/dashboard/dash_create";
            }
        }

        articleService.saveArticle(articleForm);

        redirectAttributes.addFlashAttribute("postCreated", true);

        return "redirect:/dashboard/admin";
    }

    @GetMapping("/article/{id}/update")
    public String update(@PathVariable("id") Integer id, Model model, Principal principal) {

        try {
            model.addAttribute("article", articleService.getArticleById(id));  // Usa ArticleService
        } catch (ArticleNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/404";
        }

        model.addAttribute("category", categoryRepo.findAll());

        String username = principal.getName();
        Optional<User> loggedUserOpt = userRepo.findByUsername(username);
        User loggedUser = loggedUserOpt.get();

        model.addAttribute("user", loggedUser);
        model.addAttribute("loggedUser", username);

        return "dashboard/dash_update";
    }

    @PostMapping("/article/{id}/update")
    public String Update(@PathVariable("id") Integer id, @Valid @ModelAttribute("article") Article articleForm,
            BindingResult bindingresult, @RequestParam("imageFile") MultipartFile imageFile,
            Model model) {

        if (bindingresult.hasErrors()) {
            model.addAttribute("articleId", id);
            return "/dashboard/dash_update";
        }
        Article existingArticle = articleService.getArticleById(id);

        existingArticle.setTitle(articleForm.getTitle());
        existingArticle.setBody(articleForm.getBody());
        existingArticle.setCategory(articleForm.getCategory());

        // Gestisci il caricamento dell'immagine, se presente
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.home") + "/uploads/";
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }
                String fileName = imageFile.getOriginalFilename();
                File file = new File(uploadDir + fileName);
                imageFile.transferTo(file);

                // Aggiorna il campo immagine con il nuovo nome file
                existingArticle.setImage("uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Errore durante il caricamento dell'immagine.");
                return "/dashboard/dash_update";
            }
        }

        articleService.saveArticle(existingArticle);

        return "redirect:/dashboard/admin";
    }

    @PostMapping("/article/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        try {
            articleService.deleteArticle(id);  // Usa ArticleService
        } catch (ArticleNotFoundException e) {
            return "error/404";
        }
        return "redirect:/dashboard/admin";
    }
}