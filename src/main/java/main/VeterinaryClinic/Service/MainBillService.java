package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Repository.Bill.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MainBillService {
    @Autowired
    private BillRepository repository;

    public List<Bill> getAll() {
        return repository.findAll();
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
