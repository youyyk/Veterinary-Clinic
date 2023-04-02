package main.VeterinaryClinic.Model;

import lombok.Data;
import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Service.GlobalService;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pets")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pet_id")
    private Long petID;

    @ManyToOne()
    @JoinColumn(name="account_id")
    private Account account;

    @Column(name="pet_name", columnDefinition = "CHAR(20)")
    private String name;
    @Column(name="gender", columnDefinition = "CHAR(10)")
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="date_of_birth")
    private Date doB;
    @Column(name="sterilization")//ทำหมัน
    private boolean sterilization;
    @Column(name="pet_type", columnDefinition = "CHAR(10)")//ประเภทสัตว์ ex. หมา แมว
    private String petType;
    @Column(name="breed", columnDefinition = "TEXT")//พันธุ์
    private String breed;

    @Column(name="remark", columnDefinition = "TEXT")//allergic
    private String remark;
    @Column(name="soft_deleted_date")
    private Date softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;

    @Lob
    @Column(name="image")
    private String image;


    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TreatmentHistory> treatmentHistories;


    public Pet() {super();}

    public Pet(String name, String gender, Date doB, boolean sterilization, String petType, String breed){
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.petType = petType;
        this.breed = breed;
        this.remark = "-";
        this.image = "";
        this.softDeleted = false;
        this.softDeletedDate = null;
    }



    public Pet(Account account, String name, String gender, Date doB, boolean sterilization, String petType, String breed, String remark) {
        this.account = account;
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.petType = petType;
        this.breed = breed;
        if (remark.isEmpty() || remark.equals("")){
            this.remark = "-";
        }else {
            this.remark = remark;
        }
        this.softDeleted = false;
        this.softDeletedDate = null;
    }

    public Pet(Account account, String name, String gender, Date doB, boolean sterilization, String petType, String breed) {
        this.account = account;
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.petType = petType;
        this.breed = breed;
        this.remark = "-";
        this.softDeleted = false;
        this.softDeletedDate = null;
    }

    public Pet(String name, String gender, Date doB, boolean sterilization, String petType, String breed, String remark){
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.petType = petType;
        this.breed = breed;
        this.remark = remark;
        this.softDeleted = false;
        this.softDeletedDate = null;
    }


    public Pet setAll(Pet pet,Pet newPet,long id) {
        pet.setPetID(id);
        pet.setName(newPet.name);
        pet.setGender(newPet.gender);
        pet.setDoB(newPet.doB);
        pet.setSterilization(newPet.sterilization);
        pet.setPetType(newPet.petType);
        pet.setBreed(newPet.breed);
        pet.setRemark(newPet.remark);

        System.out.println("Empty Image : "+(newPet.image).isEmpty());

        if (!((newPet.image).isEmpty())){
            System.out.println("--- Not Empty ---");
            pet.setImage(newPet.image);
        }

        return pet;
    }

    public String findAge(){
        System.out.println("---- find Age ----");
        LocalDate birthDate = this.doB.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("Date of Birth : "+birthDate);
        Date todayDate = new Date();
        LocalDate today = todayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("Today : "+today);

        Period period = Period.between(birthDate, today);
        System.out.println(period.getYears()+" years "+period.getMonths()+" months "+period.getDays()+" days");
        String age = "";

        if (period.getYears() > 1){
            age = age.concat(period.getYears()+" yrs ");
        }
        else if (period.getYears() == 1){
            age = age.concat(period.getYears()+" yr ");
        }

        if(period.getMonths() > 1){
            age = age.concat(period.getMonths()+" mos ");
        }
        else if (period.getMonths() == 1) {
            age = age.concat(period.getMonths()+" mo ");
        }

        if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() > 1){
            age = age.concat(period.getDays() + " days");
        }
        else if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() == 1){
            age = age.concat(period.getDays() + " day");
        }

        return age;



    }


    @Override
    public String toString() {
        return "Pet{" +
                "petID=" + petID +
                ", account=" + account +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", doB=" + doB +
                ", sterilization=" + sterilization +
                ", petType='" + petType + '\'' +
                ", breed='" + breed + '\'' +
                ", remark='" + remark + '\'' +
                ", softDeletedDate=" + softDeletedDate +
                ", softDeleted=" + softDeleted +
                '}';
    }
}
