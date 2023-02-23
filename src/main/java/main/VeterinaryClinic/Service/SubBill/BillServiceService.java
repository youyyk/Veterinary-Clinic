package main.VeterinaryClinic.Service.SubBill;

import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Repository.Bill.BillServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceService {
    @Autowired
    private BillServiceRepository repository;

    public List<BillServing> getAll() {
        return repository.findAll();
    }

    public BillServing save(BillServing billServing) {
        return repository.save(billServing);
    }

//    public BillServing findByBillIDAndServingID(long billID, long servingID) {
//        return repository.findByBill_idAndServing_id(billID, servingID);
//    }

}
