package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Config.SecurityConfig;
import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.AppointmentService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private PetService petService;
    @Autowired
    private AppointmentService appointmentService;

    /*
    http://localhost:8080/employees?pageSize=5
    http://localhost:8080/employees?pageSize=5&pageNo=1
    http://localhost:8080/employees?pageSize=5&pageNo=2
    http://localhost:8080/employees?pageSize=5&pageNo=1&sortBy=email
    * */
    @GetMapping
    public String getAllAccounts(
                @RequestParam(defaultValue = "1") Integer pageNo,
                @RequestParam(defaultValue = "10") Integer pageSize,
                @RequestParam(defaultValue = "firstName") String sortBy,
                Model model) {
        Page<Account> pagedResult = accountService.getAll(pageNo-1, pageSize, sortBy);
        model.addAttribute("accounts", pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>());
        model.addAttribute("pageNo", pagedResult);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalAccounts", pagedResult.getTotalElements());
        model.addAttribute("roles", new String[]{"ADMIN", "OFFICER", "CUSTOMER"});
        return "/account/accounts";
    }

    @GetMapping("/{field}")
    public List<Account> getAllAccounts(@PathVariable String field) {
        return accountService.getAccountWithSort(field);
    }

    @PostMapping("/update/role")
    public ResponseEntity getUpdateUserById(@RequestBody Map<String, String> payload) {
        if (payload.containsKey("id") && payload.containsKey("newRole")){
            Account account = accountService.getById(payload.get("id"));
            String newRole = "ROLE_" + payload.get("newRole");
            if (account != null) {
                account.setRoles(new HashSet<>());
                if (newRole.equals(SecurityConfig.ROLE_ADMIN)){
                    accountService.addRoleAdmin(account);
                } else if (newRole.equals(SecurityConfig.ROLE_OFFICER)){
                    accountService.addRoleOfficer(account);
                } else if (newRole.equals(SecurityConfig.ROLE_CUSTOMER)){
                    accountService.addRoleCustomer(account);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @GetMapping("/getInfo/{accId}")
//    public String getInfo(@PathVariable("accId") UUID accId,
//            @RequestParam(defaultValue = "1") Integer pageNo,
//            @RequestParam(defaultValue = "10") Integer pageSize,
////            @RequestParam(defaultValue = "petID") String sortBy,
//            Model model) {
//
//        System.out.println("---Get Info---");
//        Account account = accountService.getById(accId);
//        System.out.println(account.getFirstName()+" "+account.getLastName());
////        List<Pet> pets = petService.findByAccountAndSoftDeleted(account,false);
//        List<Appointment> appointments = appointmentService.findByPet_Account_AccIdOrderByDateAsc(accId);
//
//        Page<Pet> pagedResult = petService.getPaginationWithAccount(pageNo-1, pageSize,account);
//        model.addAttribute("pets", pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>());
//        model.addAttribute("pageNo", pagedResult);
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", pagedResult.getTotalPages());
//        model.addAttribute("totalAccounts", pagedResult.getTotalElements());
//        model.addAttribute("account", account);
////        model.addAttribute("pets", pets);
//        model.addAttribute("appointments", appointments);
//
//        return "account/infoAccount";
//    }

    @GetMapping("/getInfo/{accId}")
    public String getInfo(@PathVariable("accId") UUID accId, Model model) {
        System.out.println("---Get Info---");
        Account account = accountService.getById(accId);
        List<Pet> pets = petService.findByAccountAndSoftDeletedOrderByPetID(account,false);
        List<Appointment> appointments = appointmentService.findByPet_Account_AccIdOrderByDateAsc(accId);

        List<String> petTypeList = petService.petTypeUnique();
        List<String> breedList = petService.breedUnique();

        System.out.println(account.getFirstName()+" "+account.getLastName());
        model.addAttribute("account", account);
        model.addAttribute("pets", pets);
        model.addAttribute("petTypeList", petTypeList);
        model.addAttribute("breedList", breedList);
        model.addAttribute("filterPets", pets);
        model.addAttribute("appointments", appointments);
        model.addAttribute("search", "");

        return "account/infoAccount";
    }

    @GetMapping("/getInfo/{accId}/search{strSearch}")
    public String getInfo(@PathVariable("accId") UUID accId,
                          @PathVariable("strSearch") String search,
                          Model model) {
        System.out.println("---Get Info (Search) : "+search+" ---");
        Account account = accountService.getById(accId);
        List<Pet> pets = petService.findByAccountAndSoftDeletedOrderByPetID(account,false);
        List<Appointment> appointments = appointmentService.findByPet_Account_AccIdOrderByDateAsc(accId);
        List<Pet> keepPet = new ArrayList<>();

        search = search.trim().toLowerCase();

        for (Pet pet:pets) {
            if (pet.getName().toLowerCase().contains(search) || pet.getPetType().toLowerCase().contains(search) || pet.getBreed().toLowerCase().contains(search)){
                keepPet.add(pet);
            }
        }
        for (Pet pet:keepPet) {
            System.out.println(pet);
        }

        System.out.println(account.getFirstName()+" "+account.getLastName());
        model.addAttribute("account", account);
        model.addAttribute("search", search);
        model.addAttribute("pets", pets);
        model.addAttribute("filterPets", keepPet);
        model.addAttribute("appointments", appointments);

        return "account/infoAccount";
    }

    @RequestMapping(path = "/edit", method = POST)
    public String editAccount(@RequestParam("accId") String accId,
                              @RequestParam("title") String title,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("address") String address,
                              @RequestParam("phone") String phone) {
        System.out.println("---Edit Account---");

        Account account = accountService.getById(accId);

        account.setTitle(title);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setAddress(address);
        account.setPhone(phone);

        System.out.println(account);

        accountService.save(account);

        return "redirect:/account/getInfo/"+accId;
    }

    @GetMapping("/register")
    public String registerAccount(@AuthenticationPrincipal AccountUserDetail accountUserDetail, Model model) {
        if (accountUserDetail != null) {
            Account nowAccount = accountUserDetail.getAccount();
            if (nowAccount != null && !nowAccount.isRegisAccount()){
                model.addAttribute("account", accountUserDetail.getAccount());
                return "account/registerAccount";
            }
        }
        return "redirect:";
    }
}
