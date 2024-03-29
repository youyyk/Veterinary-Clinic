package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Config.SecurityConfig;
import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Service.MainBillService;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
                                    @RequestParam("newDescription") String newDescription,
                                    @RequestParam("cureAmount") int cureAmount){
        System.out.println("----- Add Medicine to Bill -----");

        Bill bill = mainBillService.findByBillID(billID);
        Medicine medicine = medicineService.findByMedID(medID);

        wareHouseService.createBillMedAndRemoveStock(bill,medicine,cureAmount,newDescription);

        return "redirect:/bill/getDetail/"+billID;
    }

    @RequestMapping(path = "/delete", method = POST)
    public String deleteMedicineFromBill(@RequestParam("billID") long billID,
                                    @RequestParam("deleteID") String deleteID){
        System.out.println("----- Delete Medicine from Bill "+billID+" -----");

        long itemID = Long.parseLong(deleteID.split("-")[1]);

        Bill bill = mainBillService.findByBillID(billID);
        Medicine medicine = medicineService.findByMedID(itemID);

        wareHouseService.deleteBillMedAndRecoverStock(bill,medicine);

        return "redirect:/bill/getDetail/"+billID;
    }

    @RequestMapping(path = "/edit", method = POST)
    public String editMedicineFromBill(@RequestParam("billID") long billID,
                                       @RequestParam("medID") long medID,
                                       @RequestParam("newDescription") String description,
                                       @RequestParam("oldAmount") int oldAmount,
                                       @RequestParam("cureAmount") int newAmount) throws ParseException {
        System.out.println("----- Edit Medicine from Bill "+billID+" -----");

        Bill bill = mainBillService.findByBillID(billID);
        Medicine medicine = medicineService.findByMedID(medID);

        if (newAmount == 0){
            wareHouseService.deleteBillMedAndRecoverStock(bill,medicine);
            return "redirect:/bill/getDetail/"+billID;
        }
        wareHouseService.editBillMedicine(bill,medicine,oldAmount,newAmount,description);

        return "redirect:/bill/getDetail/"+billID;
    }



}
