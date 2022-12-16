package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/warehouse")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private MedicineService medicineService;

//    @GetMapping
//    public List<WareHouse> getAllWarehouse() {
//        List<WareHouse> keep = wareHouseService.getAll();
////        for (WareHouse index:keep) {
////            if (index.getMedicine().getMedID() != null ) {
////                System.out.println(index.getMedicine().getName());
////            }
////        }
//        return wareHouseService.getAll();
//    }

    @GetMapping
    public String getWarehousePage(Model model) {

        // step 1. update model for template
        model.addAttribute("warehouses", wareHouseService.getAll());

        // step 2. choose HTML template
        return "warehouses";
    }

}
