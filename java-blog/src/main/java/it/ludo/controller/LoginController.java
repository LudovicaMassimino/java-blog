// package it.ludo.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import jakarta.servlet.http.HttpServletRequest;

// @Controller
// public class LoginController {

//     @Autowired
//     private AuthenticationManager authenticationManager;

//     @GetMapping("/login")
//     public String loginForm() {
//         return "/home/index";
//     }

//     @PostMapping("/authentication")
//     public String loginSubmit(
//             @RequestParam("username") String username,
//             @RequestParam("password") String password,
//             HttpServletRequest request) {

//         // Non ho bisogno di autenticare manualmente. La gestione dell'autenticazione
//         // avviene tramite Spring Security
//         return "redirect:/home/index"; // Successo, reindirizza alla pagina home
//     }

// }
