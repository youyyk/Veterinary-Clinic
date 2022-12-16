package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Service.MainBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private MainBillService mainBillService;

    @GetMapping
    public List<Bill> getAllBill() {
        return mainBillService.getAll();
    }

}
