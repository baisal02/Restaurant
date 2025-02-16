package repositories;

import entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo  extends JpaRepository<Restaurant,Long> {
    boolean existsByName(String name);
}
