package it.ludo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String loginForm() {
        return "/home/login";
    }

    @PostMapping("/authentication")
    public String loginSubmit(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request) {

        // Non hai bisogno di autenticare manualmente. La gestione dell'autenticazione avviene tramite Spring Security
        return "redirect:/home"; // Successo, reindirizza alla pagina home
    }

}

