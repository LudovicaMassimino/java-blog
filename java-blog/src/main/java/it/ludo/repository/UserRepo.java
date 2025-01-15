package it.ludo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import it.ludo.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
