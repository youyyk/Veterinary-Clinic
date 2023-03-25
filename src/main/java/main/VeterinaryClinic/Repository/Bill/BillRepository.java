package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Transactional
    Bill findByBillID(long billID);
    @Transactional
    List<Bill> findByPaidStatusIsFalseOrderByStartDateAsc();
    Bill findFirstByPaidStatusOrderByBillIDDesc(boolean paidStatus);
    @Transactional
    void deleteBillByBillID(long billID);


}
