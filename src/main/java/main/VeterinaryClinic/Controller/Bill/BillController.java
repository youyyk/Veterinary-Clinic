package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.MainBillService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static com.nimbusds.openid.connect.sdk.assurance.claims.ISO3166_1Alpha3CountryCode.THA;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private MainBillService mainBillService;

    @Autowired
    private PetService petService;


    @GetMapping()
    public String getPetPage(Model model) {
        System.out.println("--Bills Page---");

        // step 1. update model for template
        model.addAttribute("bills", mainBillService.getAll());

        // step 2. choose HTML template
        return "bill/bills";
    }

    @GetMapping("/getDetail/{billID}")
    public String getDetail(@PathVariable("billID") long billID, Model model) {
        System.out.println("---Get Bill ID : "+billID+" ---");
        Bill bill = mainBillService.findByBillID(billID);

        Pet pet = petService.findByPetID(bill.getTreatmentHistory().getPet().getPetID());
        System.out.println(pet);

        double sumTool=0,sumMed=0,sumServing=0,sumBill;

        for (BillMedicine medicine:bill.getMedUsed()){
            sumMed += (medicine.getMedTotal()*medicine.getMedicine().getPrice());
        }

        for (BillTool tool:bill.getToolUsed()){
            sumTool += (tool.getToolTotal()*tool.getTool().getPrice());
        }

        for (BillServing serving:bill.getServiceUsed()){
            sumServing += (serving.getServingTotal()*serving.getServing().getPrice());
        }

        sumBill = sumMed+sumTool+sumServing;

        System.out.println("Med total : "+sumMed);
        System.out.println("Tool total : "+sumTool);
        System.out.println("Serving total : "+sumServing);
        System.out.println("Bill total : "+sumBill);

        bill.setTotal(sumBill);
        mainBillService.save(bill);

        model.addAttribute("pet", pet);
        model.addAttribute("bill", bill);
        model.addAttribute("sumMed", sumMed);
        model.addAttribute("sumTool", sumTool);
        model.addAttribute("sumServing", sumServing);
        model.addAttribute("sumBill", sumBill);

        return "bill/billDetail";
    }

    @RequestMapping(path = "/paid", method = POST)
    public String deletePet(@RequestParam("billID") long billID){
        System.out.println("----- Paid -----");

        Bill bill = mainBillService.findByBillID(billID);

        bill.setPayType("PromptPay");
        bill.setPaidStatus(true);
        bill.setReceive(bill.getTotal());
        bill.setEndDate(GlobalService.getCurrentTime());

        mainBillService.save(bill);


        return "redirect:/bill/getDetail/"+billID;
    }






}
