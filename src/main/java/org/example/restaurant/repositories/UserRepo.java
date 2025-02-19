package org.example.restaurant.repositories;

import org.example.restaurant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByFirstName(String firstName);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.restaurant IS NULL")
    List<User> findUsersWithNoRestaurant();
}
