package main.VeterinaryClinic.Service.SubBill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Repository.Bill.BillMedicineRepository;
import main.VeterinaryClinic.Service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillMedicineService {
    @Autowired
    private BillMedicineRepository repository;
    @Autowired
    private MedicineService medicineService;

    public List<BillMedicine> getAll() {
        return repository.findAll();
    }

    public BillMedicine save(BillMedicine billMedicine) {
        return repository.save(billMedicine);
    }


    public BillMedicine findByBillIDAndMedID(long billID,long medID) {
        return repository.findByPairedID_BillAndPairedID_Med(billID, medID);
    }


}
