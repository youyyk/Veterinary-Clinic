package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.*;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.Construct.MedicineAmt;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/treatmentHistory")
public class TreatmentHistoryController {
    @Autowired
    private TreatmentHistoryService treatmentHistoryService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PetService petService;
    @Autowired
    private MainBillService mainBillService;
    @Autowired
    private BillMedicineService billMedicineService;
    @Autowired
    private BillServiceService billServiceService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/pet{petID}")
    public String getInfo(@PathVariable("petID") long petID,
                          @AuthenticationPrincipal AccountUserDetail accountUserDetail,
                          Model model) {
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            if (!accountService.getById(accountUserDetail.getAccount().getAccId()).isRegisAccount()) {
                return "redirect:/account/register";
            }
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
        }
        System.out.println("---Get Pet's Treatment History---");
        Pet pet = petService.findByPetID(petID);
        // Want treatmentHis with bill unpaid status
        List<List<MedicineAmt>> allMedicineAmt = new ArrayList<>();
        List<TreatmentHistory> wantTreatmentHistories = new ArrayList<>();
        for (TreatmentHistory treatmentHistory : treatmentHistoryService.findByPet(pet)){
            if (treatmentHistory.getBill().isPaidStatus()){
                wantTreatmentHistories.add(treatmentHistory);
                List<MedicineAmt> medicineAmts  = billMedicineService.countMedicine(treatmentHistory.getBill());
                allMedicineAmt.add(medicineAmts);
            }
        }
//        for (TreatmentHistory treat:treatmentHistories) {
//            List<MedicineAmt> medicineAmts  = billMedicineService.countMedicine(treat.getBill());
//            allMedicineAmt.add(medicineAmts);
//        }
        System.out.println("------- show ------");
        for (List<MedicineAmt> med:allMedicineAmt) {
            System.out.println(med);
        }

        List<String> petTypeList = petService.petTypeUnique();
        List<String> breedList = petService.breedUnique();

        model.addAttribute("pet", pet);
        model.addAttribute("treatmentHistories", wantTreatmentHistories);
        model.addAttribute("treatSize",wantTreatmentHistories.size());
        model.addAttribute("allMedicineAmt",allMedicineAmt);
        model.addAttribute("petTypeList", petTypeList);
        model.addAttribute("breedList", breedList);

        return "treatmentHistory/treatmentHistory";
    }

    @RequestMapping(path = "/receive/treatment", method = POST)
    public String editAccount(@RequestParam("petID") long petID,
                              @RequestParam("cc") String cc,
                              @RequestParam("ht") String ht,
                              @RequestParam("weight") double weight,
                              @RequestParam("temp") double temp,
                              @Param("appointmentId") long appointmentId) {
        System.out.println("---Receive Treatment---");
        TreatmentHistory treatmentHistory = new TreatmentHistory(petService.findByPetID(petID), GlobalService.getCurrentTime(), cc, ht, weight, temp);
        Bill bill = new Bill();
        bill.setStartDate(GlobalService.getCurrentTime());
        treatmentHistory.setBill(bill);
        bill.setTreatmentHistory(treatmentHistory);
        treatmentHistoryService.save(treatmentHistory);
        Bill findBill = mainBillService.findFirstByPaidStatusOrderByBillIDDesc(false);
        System.out.println("Bill : "+findBill);

        System.out.println(appointmentId);
        if (appointmentId >= 0) {
            Appointment appointment = appointmentService.findByAppointmentID(appointmentId);
            if (appointment != null){
                appointment.setStatus(true);
                findBill.setAppointmentStatus(true);
            }
            appointmentService.save(appointment);
            mainBillService.save(findBill);
        }


        return "redirect:/dashboard"; // Every one go to queue in dashboard
//        return "redirect:/bill/getDetail/"+findBill.getBillID();
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
    @RequestMapping(path = "/CC/edit", method = POST)
    public String editCC(@RequestParam("billID") long billID,
                                @RequestParam("cc") String cc) {
        System.out.println("---Edit CC---");
        Bill bill = mainBillService.findByBillID(billID);

        if (!cc.isEmpty() || !cc.isBlank() || !(cc == null)){
            bill.getTreatmentHistory().setCc(cc);
            mainBillService.save(bill);
        }
        else {
            bill.getTreatmentHistory().setCc("");
            mainBillService.save(bill);
        }

        return "redirect:/bill/getDetail/"+billID;
    }

    @RequestMapping(path = "/HT/edit", method = POST)
    public String editHT(@RequestParam("billID") long billID,
                         @RequestParam("ht") String ht) {
        System.out.println("---Edit HT---");
        Bill bill = mainBillService.findByBillID(billID);

        if (!ht.isEmpty() || !ht.isBlank() || !(ht == null)){
            bill.getTreatmentHistory().setHt(ht);
            mainBillService.save(bill);
        }
        else {
            bill.getTreatmentHistory().setHt("");
            mainBillService.save(bill);
        }

        return "redirect:/bill/getDetail/"+billID;
    }
    @RequestMapping(path = "/weightTemp/edit", method = POST)
    public String editWeight(@RequestParam("billID") long billID,
                             @RequestParam("weight") double weight,
                             @RequestParam("temp") double temp) {
        System.out.println("---Edit Weight---");
        Bill bill = mainBillService.findByBillID(billID);

        bill.getTreatmentHistory().setWeight(weight);
        bill.getTreatmentHistory().setTemperature(temp);
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
