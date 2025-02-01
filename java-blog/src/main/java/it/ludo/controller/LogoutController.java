package it.ludo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class LogoutController {

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/logout-success";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "redirect:/home"; 
    }
}



    // oppure si può fare con GET grazie alla gestione delle richieste di Spring Security
    // @GetMapping("/logout")
    // public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    //     if (authentication != null) {
    //         new SecurityContextLogoutHandler().logout(request, response, authentication);
    //     }
    //     return "redirect:/logout-success";
    // }

    // @GetMapping("/logout-success")
    // public String logoutSuccess() {
    //     return "/home"; // Renderizza alla home
    // }
