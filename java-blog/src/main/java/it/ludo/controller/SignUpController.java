package it.ludo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.ludo.model.User;
import it.ludo.repository.UserRepo;

@Controller
@RequestMapping
public class SignUpController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/home/signup")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> signUpNewUser(
            @RequestParam String username,
            @RequestParam String password) {

        Map<String, Object> response = new HashMap<>();

        if (userRepo.findByUsername(username).isPresent()) {
            response.put("success", false);
            response.put("message", "Username già esistente!");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response); // <-- Rispondi 400 se username esiste
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepo.save(user);

        response.put("success", true);
        response.put("message", "Registrazione avvenuta con successo!");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response); // <-- Rispondi 200 se tutto ok
    }
}