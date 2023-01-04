package main.VeterinaryClinic.Model.Account;

import lombok.Data;
import main.VeterinaryClinic.Config.SecurityConfig;
import org.bouncycastle.util.Objects;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Role() {super();}

    public Role(String name) {this.name = name;}

    // Override for use contain method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return this.name.equals(role.getName());
    }

    @Override
    public int hashCode() {
        if (name.equals(SecurityConfig.ROLE_ADMIN))
            return 0;
        else if (name.equals(SecurityConfig.ROLE_OFFICER))
            return 1;
        return 2;
    }
}
