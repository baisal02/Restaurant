package org.example.restaurant.repositories;

import org.example.restaurant.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepo extends JpaRepository<MenuItem,Long> {
    MenuItem findMenuItemByName(String name);

    MenuItem findMenuItemById(Long id);

    List<MenuItem> findByNameStartingWith(String firstCharacter);
}
