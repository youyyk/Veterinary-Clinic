package main.VeterinaryClinic.Model.Account;



import lombok.Data;
import main.VeterinaryClinic.Config.SecurityConfig;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.WareHouse;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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
    @Column(name = "address", columnDefinition="TEXT")
    private String address;
    @Column(name = "phone", columnDefinition = "CHAR(10)")
    private String phone;
    @Column(name = "line_name")
    private String lineName;
    @Column(name = "line_id")
    private String lineId;
    @Column(name = "img_path")
    private String img_path;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Account() {super();} // Need for get data from database
    public Account(String displayName, String lineId, String imgPath) {
        this.title = " ";
        this.lineName = displayName;
        this.lineId = lineId;
        this.img_path = imgPath;
    }




    public void addRole(Role role){
        roles.add(role);
    }

    public String getFullName(){
        String title = this.title==null?"":this.title+" ";
        String firstname = this.firstName==null?"":this.firstName+" ";
        String lastname = this.lastName==null?"":this.lastName;
        String fullName = title+firstname+lastname;
        if (fullName.isBlank()||fullName.isEmpty()){
            return this.lineName;
        }
        return fullName;
    }

    public String getUserRole(){
        // If edit need to follow edit in on frontend
        if (isAdmin()){
            return "ADMIN";
        }
        if (isOfficer()){
            return "OFFICER";
        }
        return "CUSTOMER";
    }

    public boolean isAdmin(){ return roles.contains(new Role(SecurityConfig.ROLE_ADMIN)); }

    public boolean isOfficer(){ return roles.contains(new Role(SecurityConfig.ROLE_OFFICER)); }

    public boolean isCustomer(){ return roles.contains(new Role(SecurityConfig.ROLE_CUSTOMER)); }

    public boolean isRegisAccount(){
        if ( (firstName == null || firstName.isBlank() || firstName.isEmpty()) ||
             (lastName == null || lastName.isBlank() || lastName.isEmpty()) ||
             (address == null || address.isBlank() || address.isEmpty()) ||
             (phone == null || phone.isEmpty() || phone.isEmpty())) {
            return false;
        }
        return true;
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
                ", lineName='" + lineName + '\'' +
                ", lineId='" + lineId + '\'' +
                ", img_path='" + img_path + '\'' +
                ", roles=" + roles +
                '}';
    }
}
