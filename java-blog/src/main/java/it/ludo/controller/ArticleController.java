package it.ludo.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.ludo.model.Article;
import it.ludo.repository.ArticleRepo;
import it.ludo.repository.CategoryRepo;
import it.ludo.repository.UserRepo;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

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
    public String readMore(@PathVariable("id") Integer id,
            @RequestHeader(value = "Referer", required = false) String referer, Model model) {
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

    @GetMapping("/dashboard/admin")
    public String index(Model model, @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "body", required = false) String body) {

        List<Article> articles = new ArrayList<>();

        if (title == null && body == null) {

            articles = articleRepo.findAll();

        } else if (title == null) {
            articles = articleRepo.findByBodyContainingIgnoreCase(body);
        } else {

            articles = articleRepo.findByTitleContainingIgnoreCase(title);
        }

        model.addAttribute("list", articles);
        return "dashboard/admin_dash";
    }

    @GetMapping("/article/{id}")
    public String show(@PathVariable("id") Integer id,
            @RequestHeader(value = "Referer", required = false) String referer, Model model) {
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
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("article", articleRepo.getReferenceById(id));

        return "dashboard/dash_update";
    }

    @PostMapping("/article/{id}/update")
    public String Update(@PathVariable("id") Integer id, @Valid @ModelAttribute("article") Article articleForm,
            BindingResult bindingresult,
            Model model) {

        if (bindingresult.hasErrors()) {
            model.addAttribute("articleId", id);
            return "/dashboard/dash_update";
        }
        Article existingArticle = articleRepo.getReferenceById(id);

        existingArticle.setTitle(articleForm.getTitle());
        existingArticle.setBody(articleForm.getBody());

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

        model.addAttribute("article", new Article());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("category", categoryRepo.findAll());

        return "/dashboard/dash_create";
    }

    @PostMapping("/dashboard/create")
    public String create(@Valid @ModelAttribute("article") Article articleForm,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepo.findAll());
            return "/dashboard/dash_create";
        }

        if (articleForm.getArticle_date() == null) {
            articleForm.setArticle_date(LocalDate.now());
        }

        MultipartFile imageFile = articleForm.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Usa un percorso stabile
                String uploadDir = "src/main/resources/static/uploads/";

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
                model.addAttribute("errorMessage", "Errore nel caricamento dell'immagine.");
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

}
