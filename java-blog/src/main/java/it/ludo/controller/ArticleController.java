package it.ludo.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.ludo.model.Article;
import it.ludo.model.Category;
import it.ludo.model.User;
import it.ludo.repository.ArticleRepo;
import it.ludo.repository.CategoryRepo;
import it.ludo.repository.UserRepo;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

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
    public String getArticles(@RequestParam(name = "category", required = false) String category, Model model) {
        List<Article> articles = articleRepo.findAll();

        if (category == null || category.isEmpty()) { // senza filtro mostra tutti gli articoli
            articles = articleRepo.findAll();
        } else {
            articles = articleRepo.findByCategoryName(category); // filtra articoli per categoria
        }

        boolean noArticles = articles.isEmpty(); // se non ci sono art per quella cat

        // Nome dell'utente loggato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("list", articles);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("noArticles", noArticles);
        model.addAttribute("loggedUser", username);

        return "home/index";
    }

    @GetMapping("/home/article/{id}")
    public String readMore(@PathVariable("id") Integer id,
            @RequestHeader(value = "Referer", required = false) String referer, Model model) {
        Article article = articleRepo.getReferenceById(id);
        model.addAttribute("article", article);

        // Nome dell'utente loggato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("loggedUser", username);

        // Determina la pagina di origine
        if (referer != null && (referer.contains("/home") || referer.contains("/dashboard/admin"))) {
            model.addAttribute("previousPage", referer);
        } else {
            model.addAttribute("previousPage", "/home"); // Default fallback
        }
        return "home/article-detail";
    }

    @GetMapping("/dashboard/admin")
    public String index(Model model, @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "body", required = false) String body,
            @RequestParam(name = "category", required = false) String category, Principal principal) {

        // Utente loggato
        String username = principal.getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<Article> articles = new ArrayList<>();

        if (title == null && body == null) {

            if (isAdmin) {
                articles = articleRepo.findAll();

            } else {
                // Assicurati di avere un metodo nel repository che filtra per autore
                articles = articleRepo.findByAuthorUsername(username);
            }
        } else if (title == null) {
            // Se c'è solo il filtro sul body
            if (isAdmin) {
                articles = articleRepo.findByBodyContainingIgnoreCase(body);
            } else {
                articles = articleRepo.findByAuthorUsernameAndBodyContainingIgnoreCase(username, body);
            }
        } else {
            // Se c'è il filtro sul titolo
            if (isAdmin) {
                articles = articleRepo.findByTitleContainingIgnoreCase(title);
            } else {
                articles = articleRepo.findByAuthorUsernameAndTitleContainingIgnoreCase(username, title);
            }
        }

        // Gestione del filtro per categoria
        if (category != null && !category.isEmpty()) {
            if (isAdmin) {
                articles = articleRepo.findByCategoryName(category);
            } else {
                articles = articleRepo.findByAuthorUsernameAndCategoryName(username, category);
            }
        }

        boolean noArticles = articles.isEmpty(); // se non ci sono art per quella cat

        // Recupera tutte le categorie ordinate
        List<Category> categories = categoryRepo.findAllByOrderByNameAsc();

        model.addAttribute("list", articles);
        model.addAttribute("category", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("noArticles", noArticles);
        model.addAttribute("loggedUser", username);
        model.addAttribute("isAdmin", isAdmin);

        return "dashboard/admin_dash";
    }

    @PostMapping("/dashboard/admin")
    public String addCategory(@RequestParam(name = "newCategory", required = false) String newCategory) {

        if (newCategory != null && !newCategory.isEmpty()) {
            // Controlla se la categoria esiste già
            Category existingCategory = categoryRepo.findByName(newCategory);
            if (existingCategory == null) {
                // Se non esiste, crea una nuova categoria
                Category newCat = new Category();
                newCat.setName(newCategory);
                categoryRepo.save(newCat); // Salva la nuova categoria nel database
            }
        }

        return "redirect:/dashboard/admin";
    }

    @GetMapping("/article/{id}")
    public String show(@PathVariable("id") Integer id,
            @RequestHeader(value = "Referer", required = false) String referer, Model model, Principal principal) {

        String username = principal.getName();
        model.addAttribute("loggedUser", username);
        Article article = articleRepo.getReferenceById(id);
        model.addAttribute("article", article);

        // Determina la pagina di origine
        if (referer != null && (referer.contains("/home") || referer.contains("/dashboard/admin"))) {
            model.addAttribute("previousPage", referer);
        } else {
            model.addAttribute("previousPage", "/home"); // Default fallback
        }
        return "home/article-detail";
    }

    @GetMapping("/article/{id}/update")
    public String update(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("article", articleRepo.getReferenceById(id));
        model.addAttribute("category", categoryRepo.findAll());

        String username = principal.getName();
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
        Article existingArticle = articleRepo.getReferenceById(id);

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

        articleRepo.save(existingArticle);

        return "redirect:/dashboard/admin";
    }

    @PostMapping("/article/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        articleRepo.deleteById(id);
        return "redirect:/dashboard/admin";
    }

    @GetMapping("/dashboard/create")
    public String create(Model model) {

        // Utente loggato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("article", new Article());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("loggedUser", username);

        return "/dashboard/dash_create";
    }

    @PostMapping("/dashboard/create")
    public String create(@Valid @ModelAttribute("article") Article articleForm,
            BindingResult bindingResult,
            Model model, Principal principal) {

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

        articleRepo.save(articleForm);
        return "redirect:/dashboard/admin";
    }

    @GetMapping("/uploads/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home") + "/uploads/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {

        // Utente loggato
        String username = principal.getName();

        model.addAttribute("loggedUser", username);

        return "home/profile";

    }
}