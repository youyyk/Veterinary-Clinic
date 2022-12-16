package main.VeterinaryClinic.Service.SubBill;

import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Repository.Bill.BillMedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillMedicineService {
    @Autowired
    private BillMedicineRepository repository;

    public List<BillMedicine> getAll() {
        return repository.findAll();
    }

    public BillMedicine create(BillMedicine billMedicine) {
        return repository.save(billMedicine);
    }


}
