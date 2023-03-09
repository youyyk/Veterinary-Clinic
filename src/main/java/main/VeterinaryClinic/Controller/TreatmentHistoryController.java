package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.MainBillService;
import main.VeterinaryClinic.Service.TreatmentHistoryService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/treatmentHistory")
public class TreatmentHistoryController {
    @Autowired
    private TreatmentHistoryService treatmentHistoryService;
    @Autowired
    private PetService petService;
    @Autowired
    private MainBillService mainBillService;


    @GetMapping("/pet{petID}")
    public String getInfo(@PathVariable("petID") long petID, Model model) {
        System.out.println("---Get Pet's Treatment History---");
        Pet pet = petService.findByPetID(petID);
        List<TreatmentHistory> treatmentHistories = treatmentHistoryService.findByPet(pet);
        System.out.println(treatmentHistories);

        model.addAttribute("pet", pet);
        model.addAttribute("treatmentHistories", treatmentHistories);

        return "treatmentHistory/treatmentHistory";
    }

    @RequestMapping(path = "/receive/treatment", method = POST)
    public String editAccount(@RequestParam("petID") long petID,
                              @RequestParam("weight") double weight) {
        System.out.println("---Receive Treatment---");

        TreatmentHistory treatmentHistory = new TreatmentHistory(petService.findByPetID(petID),
                GlobalService.getCurrentTime(),weight);
        Bill bill = new Bill();
        bill.setStartDate(GlobalService.getCurrentTime());
        treatmentHistory.setBill(bill);
        bill.setTreatmentHistory(treatmentHistory);
        treatmentHistoryService.save(treatmentHistory);
        Bill findBill = mainBillService.findFirstByPaidStatusOrderByBillIDDesc(false);
        System.out.println("Bill : "+findBill);

        return "redirect:/bill/getDetail/"+findBill.getBillID();
    }

    @RequestMapping(path = "/diagnosis/edit", method = POST)
    public String editDiagnosis(@RequestParam("billID") long billID,
                              @RequestParam("diagnosis") String diagnosis) {
        System.out.println("---Edit Diagnosis---");
        Bill bill = mainBillService.findByBillID(billID);

        if (!diagnosis.isEmpty() || !diagnosis.isBlank() || !(diagnosis == null)){
            bill.getTreatmentHistory().setDiagnosis(diagnosis);
            mainBillService.save(bill);
        }
        else {
            bill.getTreatmentHistory().setDiagnosis("");
            mainBillService.save(bill);
        }

        return "redirect:/bill/getDetail/"+billID;
    }
    @RequestMapping(path = "/weight/edit", method = POST)
    public String editWeight(@RequestParam("billID") long billID,
                              @RequestParam(defaultValue = "-1") double weight) {
        System.out.println("---Edit Weight---");
        Bill bill = mainBillService.findByBillID(billID);

        bill.getTreatmentHistory().setWeight(weight);
        mainBillService.save(bill);

        return "redirect:/bill/getDetail/"+billID;
    }

    @RequestMapping(path = "/edit", method = POST)
    public String editAccount(@RequestParam("treatmentHisID") long treatmentHisID,
                              @RequestParam("weight") double weight,
                              @RequestParam("diagnosis") String diagnosis) {
        System.out.println("---Edit Treatment History---");
        TreatmentHistory treatmentHistory = treatmentHistoryService.findByTreatmentHisID(treatmentHisID);

        if (!diagnosis.isEmpty() || !diagnosis.isBlank() || !(diagnosis == null)){
            treatmentHistory.setDiagnosis(diagnosis);
        }
        else {
            treatmentHistory.setDiagnosis("");
        }
        treatmentHistory.setWeight(weight);

        treatmentHistoryService.save(treatmentHistory);

        return "redirect:/treatmentHistory/pet"+treatmentHistory.getPet().getPetID();
    }


}
