package main.VeterinaryClinic.Service.SubBill;

import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Repository.Bill.BillToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillToolService {
    @Autowired
    private BillToolRepository repository;

    public List<BillTool> getAll() {
        return repository.findAll();
    }

    public BillTool save(BillTool billTool) {
        return repository.save(billTool);
    }

//    public BillTool findByBillIDAndToolID(long billID, long toolID) {
//        return repository.findByBill_idAndTool_id(billID, toolID);
//    }


}
