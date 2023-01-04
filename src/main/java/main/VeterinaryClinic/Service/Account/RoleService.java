package main.VeterinaryClinic.Service.Account;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Account.Role;
import main.VeterinaryClinic.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role create(String name) {
        return roleRepository.save(new Role(name));
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
