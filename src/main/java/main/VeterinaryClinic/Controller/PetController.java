package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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


    //Create Pet (PopUp) -> Return Pet's Owner Page
    @RequestMapping(path = "/pets/create", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String createPet(@RequestParam("accId") String accId,
                            @RequestParam("name") String name,
                            @RequestParam("image") MultipartFile image,
                            @RequestParam("gender") String gender,
                            @RequestParam("doB") String doB,
                            @RequestParam("sterilization") boolean sterilization,
                            @RequestParam("petType") String petType,
                            @RequestParam("breed") String breed,
                            @RequestParam("remark") String remark){

        System.out.println("---- Create Pet ----");
        System.out.println("ID : "+accId);
        Account account = accountService.getById(accId);
        System.out.println(account);
        if (remark == null || remark.isEmpty() || remark.isBlank()) remark = "-";
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

        petService.save(pet);

        return "redirect:/account/getInfo/"+accId;
    }

    //Edit Pet (PopUp) -> Return Pet's Owner Page
    @RequestMapping(path = "/pets/edit", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String editPet(@RequestParam("accId") String accId,
                          @RequestParam("page") String page,
                          @RequestParam("petID") long petID,
                          @RequestParam("name") String name,
                          @RequestParam("image") MultipartFile image,
                          @RequestParam("gender") String gender,
                          @RequestParam("doB") String doB,
                          @RequestParam("sterilization") boolean sterilization,
                          @RequestParam("petType") String petType,
                          @RequestParam("breed") String breed,
                          @RequestParam("remark") String remark){
        System.out.println("---- Edit Pet ----");
        if (remark.isEmpty() || remark.trim().isEmpty() || remark == null){
            remark = "-";
        }
        Pet pet = new Pet(name,gender, GlobalService.convertStringToDate(doB),sterilization,petType,breed,remark);
//        System.out.println(pet);

        String filename = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
//        System.out.println("FileName : " + filename);
        if (filename.contains("..")) {
            System.out.println("---- Invalid Image ----");
        }
        else if (filename.isEmpty()) {
            pet.setImage("");
        }
        else {
            try {
                System.out.println("FileName : " + filename);
                pet.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        petService.editPet(pet,petID);

        if (page.equals("infoAccount")){
            System.out.println("++++ Redirect to InfoAccount ++++");
            return "redirect:/account/getInfo/"+accId;
        }
        else if (page.equals("treatmentHistory")) {
            System.out.println("++++ Redirect to TreatmentHistory ++++");
            return "redirect:/treatmentHistory/pet"+petID;
        }
        else {
            System.out.println("---- Cannot Redirect ----");
            return null;
        }

    }

    //Delete Pet (PopUp) -> Return Pet's Owner Page
    @RequestMapping(path = "/pets/delete", method = POST)
    public String deletePet(@RequestParam("id") long id,
                            @RequestParam("pathId") String pathId){
        System.out.println("---- Delete Pet ----");

        Pet pet = petService.findByPetID(id);

        pet.setSoftDeleted(true);
        pet.setSoftDeletedDate(GlobalService.getCurrentTime());

        petService.save(pet);

        System.out.println("Delete "+pet.getName());

        return "redirect:/account/getInfo/"+pathId;
    }




}
