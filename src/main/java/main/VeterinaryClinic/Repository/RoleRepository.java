package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}