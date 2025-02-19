package org.example.restaurant.repositories;

import org.example.restaurant.entities.StopList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopListRepo extends JpaRepository<StopList,Long> {

}
