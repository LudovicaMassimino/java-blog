package it.ludo.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionHandlingController {

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFound() {
        return "redirect:/login?error=usernotfound";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException() {
        return "redirect:/login?error=auth";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException() {
        return "error"; // Gestisci errore generico
    }

}
