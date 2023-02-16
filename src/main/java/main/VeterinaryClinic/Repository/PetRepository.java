package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Pet findByPetID(long petID);

    List<Pet> findByAccount(Account account);

    List<Pet> findByAccountAndSoftDeleted(Account account,boolean softDeleted);

}
