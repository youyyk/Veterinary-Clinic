package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Transactional
    Bill findByBillID(long billID);

//    List<Pet> findByAccount(Account account);

//    Bill findFirstByStartDateOrderByBillIDesc(Date startDate);
    Bill findFirstByPaidStatusOrderByBillIDDesc(boolean paidStatus);
    @Transactional
    void deleteBillByBillID(long billID);


}
