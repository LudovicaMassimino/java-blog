package it.ludo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ludo.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
    
}
