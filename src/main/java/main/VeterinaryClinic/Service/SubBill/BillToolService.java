package main.VeterinaryClinic.Service.SubBill;

import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Repository.BillMedicineRepository;
import main.VeterinaryClinic.Repository.BillToolRepository;
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

    public BillTool create(BillTool billTool) {
        return repository.save(billTool);
    }


}
