package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MedicineService {
    @Autowired
    private MedicineRepository repository;

    public List<Medicine> getAll() {
        return repository.findAll();
    }

    public Medicine save(Medicine medicine) {
        return repository.save(medicine);
    }

    public Medicine findByMedID(long medID){return repository.findByMedID(medID);}


}