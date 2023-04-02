package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Repository.Bill.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Transactional
@Service
public class MainBillService {
    @Autowired
    private BillRepository repository;

    public List<Bill> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "billID"));
    }

    public List<Bill> findByQueueStatusIsTrueOrderByBillIDDesc() {
        return repository.findByQueueStatusIsTrueOrderByBillIDDesc();
    }
    public List<Bill> findByQueueStatusIsFalse() {
        return repository.findByQueueStatusIsFalse();
    }

    public List<Bill> findByPaidStatusIsFalseOrderByStartDateAsc(){return repository.findByPaidStatusIsFalseOrderByStartDateAsc();}

    public List<Bill> getAllFilterQueuePaidStatus(boolean queueStatus, boolean paidStatus){
        return repository.getAllFilterQueuePaidStatus(queueStatus, paidStatus, Sort.by(Sort.Direction.ASC, "startDate"));
    }
    public List<Bill> getAllFilterAppointmentQueuePaidStatus(boolean appointmentStatus, boolean queueStatus, boolean paidStatus){
        return repository.getAllFilterAppointmentQueuePaidStatus(appointmentStatus, queueStatus, paidStatus, Sort.by(Sort.Direction.ASC, "startDate"));
    }

    public Bill save(Bill bill) {
        return repository.save(bill);
    }

    public Bill findByBillID(long id){ return repository.findByBillID(id);}

//    public Bill findFirstByStartDateOrderByBillIDesc(Date startDate){return repository.findFirstByStartDateOrderByBillIDesc(startDate);}
    public Bill findFirstByPaidStatusOrderByBillIDDesc(boolean paidStatus){return repository.findFirstByPaidStatusOrderByBillIDDesc(paidStatus);}

    public void deleteBillByBillID(long billID){
        repository.deleteBillByBillID(billID);
    }


}
