package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
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

    public List<Medicine> findBySoftDeleted(boolean want){return repository.findBySoftDeleted(want);}

    public void updateMedicine(Medicine oldMedicine, Medicine newMedicine){
        if (oldMedicine.getPrice() != newMedicine.getPrice()){
            delete(oldMedicine);
            repository.save(newMedicine);
        } else {
            oldMedicine.updateFieldForEdit(newMedicine.getName(), newMedicine.getUnit(), newMedicine.getPrice(), newMedicine.getDescription(), newMedicine.getDose());
            repository.save(oldMedicine);
        }
    }
    public boolean delete(Medicine medicine){
        if (medicine.isCanDelete()){
            repository.delete(medicine);
            return true;
        }
        medicine.setSoftDeleted(true);
        medicine.setSoftDeletedDate(GlobalService.getCurrentTime());
        repository.save(medicine);
        return false;
    }
}