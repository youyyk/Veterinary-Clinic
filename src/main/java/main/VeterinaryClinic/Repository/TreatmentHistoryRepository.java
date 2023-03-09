package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TreatmentHistoryRepository extends JpaRepository<TreatmentHistory, Long> {
    @Transactional
    TreatmentHistory findByTreatmentHisID(long treatID);
    List<TreatmentHistory> findByPet(Pet pet);


    TreatmentHistory findByBill(Bill bill);
}
