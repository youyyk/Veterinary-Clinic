package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Medicine findByMedID(long medID);
}