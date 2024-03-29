package main.VeterinaryClinic.Controller;

import com.nimbusds.jose.shaded.json.JSONObject;
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
import org.springframework.http.MediaType;
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
                @RequestParam(defaultValue = "7") Integer pageSize,
                @RequestParam(defaultValue = "firstName") String sortBy,
                @AuthenticationPrincipal AccountUserDetail accountUserDetail,
                Model model) {
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            if (!accountService.getById(accountUserDetail.getAccount().getAccId()).isRegisAccount()) {
                return "redirect:/account/register";
            }
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
        }
        Page<Account> pagedResult = accountService.getAll(pageNo-1, pageSize, sortBy);
        model.addAttribute("accounts", pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>());
        model.addAttribute("pageNo", pagedResult);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("search", "");
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalAccounts", pagedResult.getTotalElements());
        model.addAttribute("roles", new String[]{"ADMIN", "OFFICER", "CUSTOMER"});

        return "/account/accounts";
    }

    @GetMapping("/search{strSearch}")
    public String searchAccount(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "7") Integer pageSize,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @PathVariable("strSearch") String search,
            @AuthenticationPrincipal AccountUserDetail accountUserDetail,
            Model model) {
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            if (!accountService.getById(accountUserDetail.getAccount().getAccId()).isRegisAccount()) {
                return "redirect:/account/register";
            }
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
        }
        search = search.trim().toLowerCase();
        if (search.isBlank() || search.isEmpty() || search.equals(null))return "redirect:/account";
        Page<Account> pagedResult = accountService.getBySearch(pageNo-1, pageSize, sortBy,search);
        model.addAttribute("accounts", pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>());
        model.addAttribute("pageNo", pagedResult);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("search", search);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalAccounts", pagedResult.getTotalElements());
        model.addAttribute("roles", new String[]{SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_OFFICER, SecurityConfig.ROLE_CUSTOMER});

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
            String newRole = payload.get("newRole");
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


    @GetMapping("/getInfo/{accId}")
    public String getInfo(@RequestParam(defaultValue = "1") Integer pageNo,
                          @RequestParam(defaultValue = "3") Integer pageSize,
                          @PathVariable("accId") UUID accId,
                          @AuthenticationPrincipal AccountUserDetail accountUserDetail,
                          Model model) {
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            if (!accountService.getById(accountUserDetail.getAccount().getAccId()).isRegisAccount()) {
                return "redirect:/account/register";
            }
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
        }
        System.out.println("---Get Info---");
        Account mainAccount = accountService.getById(accId);

//        List<Pet> pets = petService.findByAccountAndSoftDeletedOrderByPetID(mainAccount,false);
        List<Appointment> appointments = appointmentService.getAllAppointmentListForTableOnAccount(accId);

        List<String> petTypeList = petService.petTypeUnique();
        List<String> breedList = petService.breedUnique();

        Page<Pet> pagedResult = petService.getPaginationWithAccount(pageNo-1, pageSize,mainAccount);
        model.addAttribute("pets", pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>());
        model.addAttribute("pageNo", pagedResult);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalAccounts", pagedResult.getTotalElements());
        model.addAttribute("account", mainAccount);
        model.addAttribute("petTypeList", petTypeList);
        model.addAttribute("breedList", breedList);
        model.addAttribute("filterPets", pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>());
        model.addAttribute("appointments", appointments);
        model.addAttribute("search", "");
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
            // accountList, roles for merge Popup
            model.addAttribute("accountsList", accountService.getAllNotIncludeNowAccountAndMainAccount(accountUserDetail.getAccount().getAccId(), mainAccount.getAccId()));
            model.addAttribute("roles", new String[]{SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_OFFICER, SecurityConfig.ROLE_CUSTOMER});
        }
        return "account/infoAccount";
    }

    @GetMapping("/getInfo/{accId}/search{strSearch}")
    public String getInfo(@RequestParam(defaultValue = "1") Integer pageNo,
                          @RequestParam(defaultValue = "3") Integer pageSize,
                          @PathVariable("accId") UUID accId,
                          @PathVariable("strSearch") String search,
                          @AuthenticationPrincipal AccountUserDetail accountUserDetail,
                          Model model) {
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            if (!accountService.getById(accountUserDetail.getAccount().getAccId()).isRegisAccount()) {
                return "redirect:/account/register";
            }
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
        }
        System.out.println("---Get Info (Search) : "+search+" ---");
        Account account = accountService.getById(accId);
        List<Pet> pets = petService.findByAccountAndSoftDeletedOrderByPetID(account,false);
        List<Appointment> appointments = appointmentService.findByPet_Account_AccIdOrderByDateAsc(accId);

        System.out.println(account.getFirstName()+" "+account.getLastName());

        Page<Pet> pagedResult = petService.getPaginationWithAccountSearch(search.trim().toLowerCase(),accId,pageNo-1, pageSize);

        model.addAttribute("pets", pets);
        model.addAttribute("pageNo", pagedResult);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalAccounts", pagedResult.getTotalElements());

        model.addAttribute("account", account);
        model.addAttribute("search", search);
//        model.addAttribute("pets", pets);
        model.addAttribute("filterPets", pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>());
        model.addAttribute("appointments", appointments);

        return "account/infoAccount";
    }
//    @ResponseBody
//    @PostMapping("/getMergeAccountFilter")
//    public ResponseEntity getTableData(@RequestBody Map<String, String> payload) {
//        JSONObject entity = new JSONObject(); // return body
//        if (payload.containsKey("search") && payload.containsKey("mainAccountId")){
//            System.out.println("Test Merge");
//            List<Account> accounts = accountService.getAllNotIncludeNowAccountWithSearch(UUID.fromString(payload.get("mainAccountId")), payload.get("search"));
//            entity.put("accounts", accounts);
//            System.out.println("Test Merge2");
//            return new ResponseEntity<>(entity, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(entity, HttpStatus.BAD_REQUEST);
//    }

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
            Account nowAccount = accountService.getById(accountUserDetail.getAccount().getAccId());
            if (nowAccount != null && !nowAccount.isRegisAccount()){ // not regis account
                model.addAttribute("account", accountUserDetail.getAccount());
                model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
                return "account/registerAccount";
            }
            return "redirect:/loginSuccess";
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/register/withoutLine", method = POST)
    public String registerAccountWithoutLine(@RequestParam("title") String title,
                                             @RequestParam("firstName") String firstName,
                                             @RequestParam("lastName") String lastName,
                                             @RequestParam("address") String address,
                                             @RequestParam("phone") String phone) {

        System.out.println("---- Register without Line ----");

        Account account = new Account(title,firstName,lastName,address,phone);
        System.out.println(account);
        Account findAccount = accountService.save(account);


        return "redirect:/account/getInfo/"+findAccount.getAccId();
    }

    @RequestMapping(path = "/merge", method = POST)
    public String registerAccount(@RequestParam("accId") String accId,
                                  @RequestParam("mergeAccId") String mergeAccId) {
        Account mainAccount = accountService.getById(accId);
        Account mergeAccount = accountService.getById(mergeAccId);
        accountService.mergeAccount(mainAccount, mergeAccount);
        accountService.deleteByAccId(mergeAccount.getAccId());
        return "redirect:/account/getInfo/"+accId;
    }
}
