package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByAccId(UUID accId);
    Account findByLineId(String lineId);

    @Query("SELECT e FROM Account as e WHERE lower(e.firstName) LIKE %:search% OR lower(e.lastName) LIKE %:search% OR e.phone LIKE %:search%")
    Page<Account> findBySearching(@Param("search") String search, Pageable pageable);

    @Transactional
    @Query("SELECT e FROM Account as e WHERE e.accId <> :accLoginId and e.accId <> :mainAccId")
    List<Account> getAllNotIncludeNowAccount(@Param("accLoginId") UUID accLoginId, @Param("mainAccId") UUID mainAccId, Sort sort);

}