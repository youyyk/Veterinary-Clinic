package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Service.SubBill.BillServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billService")
public class BillServiceController {
    @Autowired
    private BillServiceService billServiceService;

    @GetMapping
    public List<BillServing> getAllBillService() {
        return billServiceService.getAll();
    }

}
