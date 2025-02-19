package org.example.restaurant.repositories;

import org.example.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo  extends JpaRepository<Restaurant,Long> {
    boolean existsByName(String name);
}
