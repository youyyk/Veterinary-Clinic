package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.Account.AccountService;
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
import java.util.UUID;

@Controller
@RequestMapping
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/pets")
    public String getPetPage(Model model) {
        System.out.println("--Pet Page---");

        // step 1. update model for template
        model.addAttribute("pets", petService.getAll());

        // step 2. choose HTML template
        return "pet/pets";
    }



    //After submit "Create Pet (PopUp)" get object from input for create pet and return to show all pets
    @PostMapping("/create/pets")
    public String createPet(@RequestParam("accId") String accId,
                            @RequestParam("name") String name,
                            @RequestParam("image") MultipartFile image,
                            @RequestParam("gender") String gender,
                            @RequestParam("doB") String doB,
                            @RequestParam("age") String age,
                            @RequestParam("sterilization") boolean sterilization,
                            @RequestParam("petType") String petType,
                            @RequestParam("breed") String breed,
                            @RequestParam("remark") String remark){
        System.out.println("---- Create Pet ----");
        System.out.println("ID : "+accId);
        Account account = accountService.getById(accId);
        System.out.println(account);
        Pet pet = new Pet(account,name,gender, GlobalService.convertStringToDate(doB),sterilization,petType,breed,remark);
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

        return "redirect:/pets";
    }

    @PostMapping("/edit/pets")
    public String editPet(@RequestParam("petID") long petID,
                          @RequestParam("name") String name,
                          @RequestParam("image") MultipartFile image,
                          @RequestParam("gender") String gender,
                          @RequestParam("doB") String doB,
                          @RequestParam("sterilization") Boolean sterilization,
                          @RequestParam("petType") String petType,
                          @RequestParam("breed") String breed,
                          @RequestParam("remark") String remark){
        System.out.println("---- Edit Pet ----");
        Pet pet = new Pet(name,gender, GlobalService.convertStringToDate(doB),sterilization,petType,breed,remark);
        System.out.println(pet);

        String filename = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        System.out.println("FileName : " + filename);
        if (filename.contains("..")) {
            System.out.println("---- Invalid Image ----");
        }
        else if (filename.isEmpty()) {
            pet.setImage("");
        }
        else {
            try {
                pet.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        petService.editPet(pet,petID);

        return "redirect:/pets";
    }



}
