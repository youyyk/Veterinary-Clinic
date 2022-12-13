package main.VeterinaryClinic.Service.Account;

import main.VeterinaryClinic.Config.AccountRepository;
import main.VeterinaryClinic.Model.Account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account create(String displayName, String lineId, String imgPath) {
        return accountRepository.save(new Account(displayName, lineId, imgPath));
    }
    public void save(Account account){accountRepository.save(account);}
    public Account getByLineId(String lineId){ return accountRepository.findByLineId(lineId);}
    public List<Account> getAll(){
        return accountRepository.findAll();
    }
}
