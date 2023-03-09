package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Medicine findByMedID(long medID);
}