package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Serving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServingRepository extends JpaRepository<Serving, Long> {
    Serving findByServingID(long servingID);
}
