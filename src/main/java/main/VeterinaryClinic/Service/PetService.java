package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    public List<Pet> findByAccountAndSoftDeletedOrderByPetID(Account account, boolean softDeleted) {
        return repository.findByAccountAndSoftDeletedOrderByPetID(account, softDeleted);
    }

    public Pet findByPetID(long petID) {
        return repository.findByPetID(petID);
    }

    public List<String> petTypeUnique(){
        return repository.getDistinctPetByPetType();
    }
    public List<String> breedUnique(){
        return repository.getDistinctPetByBreed();
    }

    public Pet save(Pet pet) {
        return repository.save(pet);
    }

    public void editPet(Pet newPet, long id) {
        System.out.println("---Edit Pet Service---");
        Pet pet = repository.findByPetID(id);
        System.out.println("Old Pet " + pet);
        pet = pet.setAll(pet, newPet, id);
        System.out.println("Set Pet " + pet);
        repository.save(pet);
        System.out.println("---done---");
    }

    public Page<Pet> getPaginationWithAccount(Integer pageNo, Integer pageSize,Account account) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return repository.findAllByAccountAndSoftDeletedIsFalseOrderByPetID(account,paging);
    }

    public Page<Pet> getPaginationWithAccountSearch(String search, UUID accId, Integer pageNo, Integer pageSize) {
//        System.out.println("pagesize : "+pageSize);
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Page<Pet> page = new PageImpl<>(pets,pageable, pets.size());
//        return page;
        Pageable paging = PageRequest.of(pageNo, pageSize);
//        System.out.println(" ++++++++++ Page ++++++++++");
//        System.out.println(repository.findByAccountBySearching(search,accId,paging));
//        System.out.println("+++++++++++++++++++++++++++");
        return repository.findByAccountBySearching(search,accId,paging);
    }
}
