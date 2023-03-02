package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Service.MainBillService;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/billMedicine")
public class BillMedicineController {
    @Autowired
    private BillMedicineService billMedicineService;
    @Autowired
    private MainBillService mainBillService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private WareHouseService wareHouseService;

    @GetMapping
    public List<BillMedicine> getAllBillMedicine() {
        return billMedicineService.getAll();
    }

    @RequestMapping(path = "/add", method = POST)
    public String addMedicineToBill(@RequestParam("billID") long billID,
                                    @RequestParam("medID") long medID,
                                    @RequestParam("cureAmount") int cureAmount){
        System.out.println("----- Add Medicine to Bill -----");

        Bill bill = mainBillService.findByBillID(billID);
        Medicine medicine = medicineService.findByMedID(medID);


        BillMedicine billMedicine = new BillMedicine(bill,medicine,cureAmount);
        billMedicineService.save(billMedicine);

        wareHouseService.removeStock(medicine,cureAmount);

        return "redirect:/bill/getDetail/"+billID;
    }

}
