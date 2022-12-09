package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;

    @GetMapping
    public List<WareHouse> getAllWarehouse() {
        return wareHouseService.getAll();
    }


}
