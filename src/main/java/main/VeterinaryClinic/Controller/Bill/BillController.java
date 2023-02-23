package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Service.MainBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private MainBillService mainBillService;


    @GetMapping()
    public String getPetPage(Model model) {
        System.out.println("--Bills Page---");

        // step 1. update model for template
        model.addAttribute("bills", mainBillService.getAll());

        // step 2. choose HTML template
        return "bill/bills";
    }

    @GetMapping("/{billID}")
    public String getInfo(@PathVariable("billID") long billID, Model model) {
        System.out.println("---Get Bill ID : "+billID+" ---");

        Bill bill = mainBillService.findByBillID(billID);

        model.addAttribute("bill", bill);



        return "bill/infoBill";
    }




}
