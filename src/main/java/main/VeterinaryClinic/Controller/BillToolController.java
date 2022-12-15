package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billTool")
public class BillToolController {
    @Autowired
    private BillToolService billToolService;

    @GetMapping
    public List<BillTool> getAllBillTool() {
        return billToolService.getAll();
    }

}
