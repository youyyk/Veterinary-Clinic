package main.VeterinaryClinic.Controller.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Model.Serving;
import main.VeterinaryClinic.Service.MainBillService;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.ServingService;
import main.VeterinaryClinic.Service.SubBill.BillServiceService;
import main.VeterinaryClinic.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/billService")
public class BillServiceController {
    @Autowired
    private BillServiceService billServiceService;
    @Autowired
    private MainBillService mainBillService;
    @Autowired
    private ServingService servingService;

    @GetMapping
    public List<BillServing> getAllBillService() {
        return billServiceService.getAll();
    }

    @RequestMapping(path = "/add", method = POST)
    public String addServingToBill(@RequestParam("billID") long billID,
                                   @RequestParam("servingID") long servingID,
                                   @RequestParam("cureAmount") int cureAmount){
        System.out.println("----- Add Serving to Bill -----");

        Bill bill = mainBillService.findByBillID(billID);
        Serving serving = servingService.findByServingID(servingID);

        BillServing billServing = new BillServing(bill,serving,cureAmount);
        billServiceService.save(billServing);

        return "redirect:/bill/getDetail/"+billID;
    }

}
