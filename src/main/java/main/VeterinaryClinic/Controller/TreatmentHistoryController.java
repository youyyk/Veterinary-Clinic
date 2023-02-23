package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.TreatmentHistory;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.TreatmentHistoryService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/treatmentHistory")
public class TreatmentHistoryController {
    @Autowired
    private TreatmentHistoryService TreatmentHistoryService;

    @Autowired
    private PetService petService;


    @GetMapping("/{petID}")
    public String getInfo(@PathVariable("petID") long petID, Model model) {
        System.out.println("---Get Pet's Treatment History---");
        Pet pet = petService.findByPetID(petID);
        List<TreatmentHistory> treatmentHistories = TreatmentHistoryService.findByPet(pet);
        System.out.println(treatmentHistories);

//        System.out.println(account.getFirstName()+" "+account.getLastName());
        model.addAttribute("pet", pet);
        model.addAttribute("treatmentHistories", treatmentHistories);


        return "treatmentHistory/treatmentHistory";
    }


}
