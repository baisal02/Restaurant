package repositories;

import entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepo extends JpaRepository<Subcategory,Long> {
    boolean existsByName(String name);
}
