package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Service;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public List<Service> getAllService() {
        return serviceService.getAll();
    }
}
