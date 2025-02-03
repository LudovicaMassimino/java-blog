package it.ludo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ludo.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    // Metodo per trovare una categoria per nome
    Category findByName(String name);

    // Trova tutte le categorie ordinate per nome
    List<Category> findAllByOrderByNameAsc();
}
