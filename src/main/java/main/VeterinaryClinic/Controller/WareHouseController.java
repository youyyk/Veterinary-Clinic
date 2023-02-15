package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.ToolService;
import main.VeterinaryClinic.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/warehouse")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private ToolService toolService;

    @GetMapping
    public String getWarehousePage(Model model) {
        model.addAttribute("warehouses", wareHouseService.getAll());
        model.addAttribute("medicines", medicineService.getAll());
        model.addAttribute("tools", toolService.getAll());
        model.addAttribute("newMedForm", new Medicine());
        model.addAttribute("newToolForm", new Tool());
        model.addAttribute("newWarehouseMedForm", new WareHouse());
        model.addAttribute("newWarehouseToolForm", new WareHouse());
        return "warehouse/warehouses";
    }

    @RequestMapping(path = "/create/medicine", method = RequestMethod.POST)
    public String createPet(@ModelAttribute Medicine medicine){
        System.out.println("Test");
        medicineService.create(medicine);
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/create/tool", method = RequestMethod.POST)
    public String createPet(@ModelAttribute Tool tool){
        toolService.create(tool);
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/create/order", method = RequestMethod.POST)
    public String createPet(@ModelAttribute WareHouse wareHouse){
        if (wareHouse.getMedicine() != null){
            wareHouseService.create(new WareHouse(wareHouse.getMedicine(), wareHouse.getQuantityIn(), wareHouse.getPaidTotal(), wareHouse.getExpiredDate()));
        } else if (wareHouse.getTool() != null){
            wareHouseService.create(new WareHouse(wareHouse.getTool(), wareHouse.getQuantityIn(), wareHouse.getPaidTotal(), wareHouse.getExpiredDate()));
        }
        return "redirect:/warehouse";
    }
}
