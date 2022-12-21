package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.CureHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CureHistoryRepository extends JpaRepository<CureHistory, Long> {
}
