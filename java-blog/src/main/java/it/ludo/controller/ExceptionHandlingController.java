package it.ludo.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionHandlingController {

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFound() {
        return "/home/index"; // Ritorna la pagina di login
    }

    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException() {
        return "/home/index"; // Gestisci errori di autenticazione
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException() {
        return "error"; // Gestisci errore generico
    }

}
