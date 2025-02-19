package org.example.restaurant.repositories;

import org.example.restaurant.entities.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChequeRepo extends JpaRepository<Cheque,Long> {
}
