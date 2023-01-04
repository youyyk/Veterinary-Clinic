package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Serving;
import main.VeterinaryClinic.Service.ServingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/serving")
public class ServiceController {
    @Autowired
    private ServingService servingService;

    @GetMapping
    public List<Serving> getAllService() {
        return servingService.getAll();
    }
}
