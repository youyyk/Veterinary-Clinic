package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Service.MainBillService;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import main.VeterinaryClinic.Service.ToolService;
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
@RequestMapping("/billTool")
public class BillToolController {
    @Autowired
    private BillToolService billToolService;
    @Autowired
    private MainBillService mainBillService;
    @Autowired
    private ToolService toolService;
    @Autowired
    private WareHouseService wareHouseService;

    @GetMapping
    public List<BillTool> getAllBillTool() {
        return billToolService.getAll();
    }

    @RequestMapping(path = "/add", method = POST)
    public String addToolToBill(@RequestParam("billID") long billID,
                                @RequestParam("toolID") long toolID,
                                @RequestParam("cureAmount") int cureAmount){
        System.out.println("----- Add Tool to Bill -----");

        Bill bill = mainBillService.findByBillID(billID);
        Tool tool = toolService.findByToolID(toolID);

        wareHouseService.createBillToolAndRemoveStock(bill,tool,cureAmount);

        return "redirect:/bill/getDetail/"+billID;
    }

    @RequestMapping(path = "/delete", method = POST)
    public String deleteMedicineFromBill(@RequestParam("billID") long billID,
                                         @RequestParam("deleteID") long deleteID){
        System.out.println("----- Delete Tool from Bill "+billID+" -----");

        Bill bill = mainBillService.findByBillID(billID);
        Tool tool = toolService.findByToolID(deleteID);

        wareHouseService.deleteBillToolAndRecoverStock(bill,tool);

        return "redirect:/bill/getDetail/"+billID;
    }

}
