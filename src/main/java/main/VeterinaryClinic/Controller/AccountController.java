package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Config.SecurityConfig;
import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

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
}
