package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Repository.MedicineRepository;
import main.VeterinaryClinic.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository repository;

    public List<Pet> getAll() {
        return repository.findAll();
    }

    public Pet create(Pet pet) {
        return repository.save(pet);
    }



}
