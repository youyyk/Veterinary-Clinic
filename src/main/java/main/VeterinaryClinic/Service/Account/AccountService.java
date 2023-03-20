package main.VeterinaryClinic.Service.Account;

import main.VeterinaryClinic.Config.SecurityConfig;
import main.VeterinaryClinic.Model.Account.Role;
import main.VeterinaryClinic.Repository.AccountRepository;
import main.VeterinaryClinic.Model.Account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    public Account create(String displayName, String lineId, String imgPath) {

        Account account = new Account(displayName, lineId, imgPath);
        save(account);
        addRoleCustomer(account);
        return account; // Link Role
    }

    public void addRoleAdmin(Account account) {
        Role role_admin = roleService.findByName(SecurityConfig.ROLE_ADMIN);
        if (role_admin == null) {
            role_admin = roleService.create(SecurityConfig.ROLE_ADMIN);
        }
        account.addRole(role_admin);
        addRoleOfficer(account);
    }

    public void addRoleOfficer(Account account) {
        Role role_officer = roleService.findByName(SecurityConfig.ROLE_OFFICER);
        if (role_officer == null) {
            role_officer = roleService.create(SecurityConfig.ROLE_OFFICER);
        }
        account.addRole(role_officer);
        addRoleCustomer(account);
    }

    public void addRoleCustomer(Account account) {
        Role role_customer = roleService.findByName(SecurityConfig.ROLE_CUSTOMER);
        if (role_customer == null) {
            role_customer = roleService.create(SecurityConfig.ROLE_CUSTOMER);
        }
        account.addRole(role_customer);
        save(account);
    }
    public Account save(Account account){
        return accountRepository.save(account);
    }

    public Account getById(String id){ return accountRepository.findByAccId(UUID.fromString(id));}
    public Account getById(UUID id){ return accountRepository.findByAccId(id);}
    public Account getByLineId(String lineId){ return accountRepository.findByLineId(lineId);}

    public List<Account> getAll(){
        return accountRepository.findAll();
    }
    public Page<Account> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return accountRepository.findAll(paging);
    }

    public Page<Account> getBySearch(Integer pageNo, Integer pageSize, String sortBy,String search) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return accountRepository.findBySearching(search,paging);
    }
    public List<Account> getAccountWithSort(String field) {
        return accountRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public long countAccount(){
        return accountRepository.count();
    }

    public void editAccount(Account newAccount,String accId){
        Account account = getById(accId);

        account.setTitle(newAccount.getTitle());
        account.setFirstName(newAccount.getFirstName());
        account.setLastName(newAccount.getLastName());
        account.setAddress(newAccount.getAddress());
        account.setPhone(newAccount.getPhone());

        accountRepository.save(account);
    }
}
