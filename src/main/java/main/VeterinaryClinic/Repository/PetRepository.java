package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
