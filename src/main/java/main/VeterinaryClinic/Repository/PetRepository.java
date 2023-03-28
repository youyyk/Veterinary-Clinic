package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface PetRepository extends JpaRepository<Pet, Long> {
    Pet findByPetID(long petID);

    List<Pet> findByAccount(Account account);

    List<Pet> findByAccountAndSoftDeletedOrderByPetID(Account account,boolean softDeleted);

    List<Pet> findByAccountContainsAndNameOrBreedOrPetTypeOrderByPetID(Account account, String name, String breed, String petType);

    @Transactional
    Page<Pet> findAllByAccountAndSoftDeletedIsFalseOrderByPetID(Account account,Pageable pageable);

//    @Query("SELECT e FROM Pet e WHERE e.account='%account%' AND e.name LIKE '%search%' OR e.breed  LIKE '%search%' OR e.petType LIKE '%search%' ORDER BY e.petID")
//    Page<Pet> findByAccountBySearching(@Param("search") String search,@Param("account") Account account,Pageable pageable);
////    @Query("SELECT e FROM Pet e WHERE e.account='%account%' AND e.name LIKE '%search%' OR e.breed  LIKE '%search%' OR e.petType LIKE '%search%' ORDER BY e.petID")
    @Query("SELECT e FROM Pet as e WHERE (e.account.accId = :accId ) and lower(e.name) LIKE %:search% OR lower(e.breed)  LIKE %:search% OR lower(e.petType) LIKE %:search% ORDER BY e.petID ASC")
    Page<Pet> findByAccountBySearching(@Param("search") String search,@Param("accId") UUID accId, Pageable pageable);


    @Query("SELECT DISTINCT e.petType FROM Pet e")
    List<String> getDistinctPetByPetType();

    @Query("SELECT DISTINCT e.breed FROM Pet e")
    List<String> getDistinctPetByBreed();



}
