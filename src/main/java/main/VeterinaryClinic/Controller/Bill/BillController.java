package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Service.*;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillServiceService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private MainBillService mainBillService;
    @Autowired
    private BillMedicineService billMedicineService;
    @Autowired
    private BillToolService billToolService;
    @Autowired
    private BillServiceService billServiceService;
    @Autowired
    private PetService petService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private ToolService toolService;
    @Autowired
    private ServingService servingService;
    @Autowired
    private WareHouseService wareHouseService;


    @GetMapping()
    public String getPetPage(Model model) {
        System.out.println("-- Bills Page ---");

        // step 1. update model for template
        model.addAttribute("bills", mainBillService.getAll());

        // step 2. choose HTML template
        return "bill/bill";
    }

    @GetMapping("/getDetail/{billID}")
    public String getDetail(@PathVariable("billID") long billID, Model model) {
        System.out.println("--- Get Bill ID : "+billID+" ---");
        Bill bill = mainBillService.findByBillID(billID);
        Pet pet = petService.findByPetID(bill.getTreatmentHistory().getPet().getPetID());
        List<Medicine> medicines = medicineService.getAll();
        List<Tool> tools = toolService.getAll();
        List<Serving> servings = servingService.getAll();

        List<BillMedicine> billMedList = billMedicineService.findByBill(bill);
        List<BillTool> billToolList = billToolService.findByBill(bill);


        //------- Count item and calculate sum --------
        double sumTool=0,sumMed=0,sumServing=0,sumBill;
        List<MedicineAmt> medicineAmts  = billMedicineService.countMedicine(bill);
        for (MedicineAmt med : medicineAmts) {
            sumMed += med.getTotalPrice();
            medicines.remove(med.getMedicine());
        }
        List<ToolAmt> toolAmts  = billToolService.countTool(bill);
        for (ToolAmt tool : toolAmts) {
            sumTool += tool.getTotalPrice();
            tools.remove(tool.getTool());
        }
        for (BillServing serving:bill.getServiceUsed()){
            sumServing += (serving.getServingTotal()*serving.getServing().getPrice());
            servings.remove(serving.getServing());
        }
        sumBill = sumMed+sumTool+sumServing;
        System.out.println("Med total : "+sumMed);
        System.out.println("Tool total : "+sumTool);
        System.out.println("Serving total : "+sumServing);
        System.out.println("Bill total : "+sumBill);
        bill.setTotal(sumBill);
        mainBillService.save(bill);


        //---- Pass Value ----
        model.addAttribute("pet", pet);
        model.addAttribute("bill", bill);

        model.addAttribute("medicineAmts",medicineAmts);
        model.addAttribute("toolAmts",toolAmts);

        model.addAttribute("billMedList",billMedList);
        model.addAttribute("billToolList",billToolList);

        model.addAttribute("medicines", medicines);
        model.addAttribute("tools", tools);
        model.addAttribute("servings", servings);

        model.addAttribute("sumMed", sumMed);
        model.addAttribute("sumTool", sumTool);
        model.addAttribute("sumServing", sumServing);
        model.addAttribute("sumBill", sumBill);

        return "bill/billDetail";
    }

    @RequestMapping(path = "/paid", method = POST)
    public String deletePet(@RequestParam("billID") long billID,
                            @RequestParam(defaultValue = "-1") double receive,
                            @RequestParam("payType") String payType){
        System.out.println("----- Paid -----");

        Bill bill = mainBillService.findByBillID(billID);


        System.out.println("payType : "+payType);
        System.out.println("Receive : "+receive);

        if (payType == "cash"){
            System.out.println("Payment : Cash");
            bill.setPayType("Cash");
            bill.setReceive(receive);
        }
        else if (payType == "prompt") {
            System.out.println("Payment : PromptPay");
            bill.setPayType("PromptPay");
            bill.setReceive(bill.getTotal());
        }
        bill.setPaidStatus(true);
        bill.setEndDate(GlobalService.getCurrentTime());

        mainBillService.save(bill);

        return "redirect:/bill/getDetail/"+billID;
    }






}
