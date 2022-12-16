package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @GetMapping
    public List<Pet> getAllPet() {
        return petService.getAll();
    }


}
