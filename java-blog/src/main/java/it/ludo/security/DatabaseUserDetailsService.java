package it.ludo.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.ludo.model.User;
import it.ludo.repository.UserRepo;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    
    @Autowired
    private final UserRepo userRepo;

    public DatabaseUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);

        if (user.isPresent()) {
            System.out.println("Trovato utente: " + user.get().getUsername());
            return new DatabaseUserDetails(user.get());
        } else {
            System.out.println("Utente non trovato: " + username);
            throw new UsernameNotFoundException("Utente non trovato");
        }
    }
}
