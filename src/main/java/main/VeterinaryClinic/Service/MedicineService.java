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

    public Medicine create(Medicine medicine) {
        return repository.save(medicine);
    }


}
