package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Transactional
    Bill findByBillID(long billID);
    @Transactional
    List<Bill> findByPaidStatusIsFalseOrderByStartDateAsc();
    @Transactional
    List<Bill> findByQueueStatusIsTrueOrderByBillIDDesc();
    @Transactional
    List<Bill> findByQueueStatusIsFalse();
    @Query("SELECT e FROM Bill as e WHERE e.queueStatus =:queueStatus and e.paidStatus =:paidStatus")
    List<Bill> getAllFilterQueuePaidStatus(@Param("queueStatus") boolean queueStatusStatus, @Param("paidStatus") boolean paidStatus, Sort sort);
    @Query("SELECT e FROM Bill as e WHERE e.appointmentStatus =:appointmentStatus and e.queueStatus =:queueStatus and e.paidStatus =:paidStatus")
    List<Bill> getAllFilterAppointmentQueuePaidStatus(@Param("appointmentStatus") boolean appointmentStatus, @Param("queueStatus") boolean queueStatusStatus, @Param("paidStatus") boolean paidStatus, Sort sort);
    Bill findFirstByPaidStatusOrderByBillIDDesc(boolean paidStatus);
    @Transactional
    void deleteBillByBillID(long billID);


}
