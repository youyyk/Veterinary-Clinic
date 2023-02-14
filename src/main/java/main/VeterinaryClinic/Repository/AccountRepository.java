package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Account.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByAccId(UUID accId);
    Account findByLineId(String lineId);


}