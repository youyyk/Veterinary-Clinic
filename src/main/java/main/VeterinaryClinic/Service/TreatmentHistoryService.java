package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Repository.TreatmentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class TreatmentHistoryService {
    @Autowired
    private TreatmentHistoryRepository repository;

    public List<TreatmentHistory> getAll() {
        return repository.findAll();
    }

    public TreatmentHistory findByTreatmentHisID(long treatID) {
        return repository.findByTreatmentHisID(treatID);
    }


    public List<TreatmentHistory> findByPet(Pet pet){return repository.findByPet(pet);}

    public TreatmentHistory save(TreatmentHistory TreatmentHistory) {
        return repository.save(TreatmentHistory);
    }






}
