package it.ludo.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.ludo.model.User;
import it.ludo.repository.UserRepo;

@Controller
@RequestMapping
public class ProfileController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {

        // Utente loggato
        String username = principal.getName();
        Optional<User> loggedUserOpt = userRepo.findByUsername(username);

        if (loggedUserOpt.isPresent()) {
            model.addAttribute("user", loggedUserOpt.get());
        } else {
            throw new RuntimeException("Utente non trovato per username: " + username);
        }

        model.addAttribute("loggedUser", username);

        return "home/profile";
    }

    @PostMapping("/profile/updatePhoto")
    public String updatePhoto(@RequestParam("photoProfile") MultipartFile photoProfile, Principal principal,
            Model model) {

        String username = principal.getName();
        Optional<User> loggedUserOpt = userRepo.findByUsername(username);

        if (loggedUserOpt.isPresent()) {
            model.addAttribute("user", loggedUserOpt.get());
        } else {
            throw new RuntimeException("Utente non trovato per username: " + username);
        }

        User user = loggedUserOpt.get();
        if (photoProfile != null && !photoProfile.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.home") + "/uploads/";

                // Crea la directory se non esiste
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                // Salva il file
                String fileName = photoProfile.getOriginalFilename();
                File file = new File(uploadDir + fileName);
                photoProfile.transferTo(file);

                // Salva il nome del file nella proprietà `image`
                user.setPhotoProfile("uploads/" + fileName);
                userRepo.save(user);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Image upload error.");
                return "/home/profile";
            }
        }

        return "redirect:/profile";
    }
}