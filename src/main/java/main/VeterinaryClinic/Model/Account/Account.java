package main.VeterinaryClinic.Model.Account;



import lombok.Data;
import main.VeterinaryClinic.Model.Role;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "account_id", columnDefinition = "CHAR(36)")
    private UUID accId;
    @Column(name = "title", columnDefinition = "CHAR(20)")
    private String title;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone", columnDefinition = "CHAR(10)")
    private String phone;
    @Column(name = "line_id")
    private String lineId;
    @Column(name = "img_path")
    private String img_path;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Account() {super();} // Need for get data from database
    public Account(String displayName, String lineId, String imgPath) {
        this.firstName = displayName;
        this.lineId = lineId;
        this.img_path = imgPath;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accId=" + accId +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", lineId='" + lineId + '\'' +
                ", img_path='" + img_path + '\'' +
                ", roles=" + roles +
                '}';
    }
}
