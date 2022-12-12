package main.VeterinaryClinic.Config;

import main.VeterinaryClinic.Model.User.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByLineId(String lineId);
}