package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billMedicine")
public class BillMedicineController {
    @Autowired
    private BillMedicineService billMedicineService;

    @GetMapping
    public List<BillMedicine> getAllBillMedicine() {
        return billMedicineService.getAll();
    }

}
