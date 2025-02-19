package org.example.restaurant.repositories;

import org.example.restaurant.entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepo extends JpaRepository<Subcategory,Long> {
    boolean existsByName(String name);
}
