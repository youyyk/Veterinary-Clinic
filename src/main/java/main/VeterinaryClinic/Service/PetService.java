package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepository repository;

    public List<Pet> getAll() {
        return repository.findAll();
    }

    public List<Pet> findByAccount(Account account) {
        return repository.findByAccount(account);
    }

    public List<Pet> findByAccountAndSoftDeleted(Account account,boolean softDeleted) {
        return repository.findByAccountAndSoftDeleted(account,softDeleted);
    }

    public Pet findByPetID(long petID) {
        return repository.findByPetID(petID);
    }

    public Pet save(Pet pet) {
        return repository.save(pet);
    }

    public void editPet(Pet newPet,long id)
    {
        System.out.println("---Edit Pet Service---");
        Pet pet = repository.findByPetID(id);
        System.out.println("Old Pet "+pet);
        pet = pet.setAll(pet,newPet,id);
        System.out.println("Set Pet "+pet);
        repository.save(pet);
        System.out.println("---done---");
    }



}
