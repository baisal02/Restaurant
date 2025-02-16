package repositories;

import entities.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChequeRepo extends JpaRepository<Cheque,Long> {
}
