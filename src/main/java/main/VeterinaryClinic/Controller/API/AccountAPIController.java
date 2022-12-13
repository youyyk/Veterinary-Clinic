package main.VeterinaryClinic.Controller.API;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountAPIController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAll(){
        return accountService.getAll();
    }
}
