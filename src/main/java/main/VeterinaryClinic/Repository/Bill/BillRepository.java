package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Transactional
    Bill findByBillID(long billID);

//    List<Pet> findByAccount(Account account);

}
