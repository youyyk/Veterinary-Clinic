package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Config.AccountRepository;
import main.VeterinaryClinic.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account create(String firstname, String lastname, String type) {
        return accountRepository.save(new Account(firstname, lastname, type));
    }

    public List<Account> findAll(){
        return accountRepository.findAll();
    }
}
