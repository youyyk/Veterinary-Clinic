package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Controller
@RequestMapping
public class PetController {
    @Autowired
    private PetService petService;


    @GetMapping("/pets")
    public String getPetPage(Model model) {

        // step 1. update model for template
        model.addAttribute("pets", petService.getAll());

        // step 2. choose HTML template
        return "pet/pets";
    }

//    html page
//    @GetMapping("/pets/create")
//    public String createPetForm(Model model) {
//        //Create Pet's object
//        Pet pet;
//
//
//        // step 1. update model for template
//        model.addAttribute("pet", petService.getAll());
//
//        // step 2. choose HTML template
//        return "createPet";
//    }

    //Create Pet (PopUp)
    @GetMapping("/pets/create")
    public String createPetForm() {
        return "../pet/petPopUp/createPetPopUp";
    }

    //After submit "Create Pet (PopUp)" get object from input for create pet and return to show all pets
    @PostMapping("/pets")
    public String createPet(@RequestParam("name") String name,
                            @RequestParam("image") MultipartFile image,
                            @RequestParam("gender") String gender,
                            @RequestParam("doB") String doB,
                            @RequestParam("age") String age,
                            @RequestParam("sterilization") boolean sterilization,
                            @RequestParam("petType") String petType,
                            @RequestParam("breed") String breed,
                            @RequestParam("remark") String remark){
        System.out.println(doB);
        Pet pet = new Pet(name,gender, GlobalService.convertStringToDate(doB),sterilization,petType,breed,remark);
        String filename = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        if (filename.contains("..")){
            System.out.println("---- Invalid Image ----");
        }
        try {
            pet.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        petService.create(pet);

        return "redirect:/pet/pets";
    }



}
