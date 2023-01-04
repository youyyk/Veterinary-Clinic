package main.VeterinaryClinic.Model;

import lombok.Data;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Service.GlobalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
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
    @Column(name="pet_name")
    private String name;
    @Column(name="gender")
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="date_of_birth")
    private Date doB;
    @Column(name="sterilization")//ทำหมัน
    private boolean sterilization;
    @Column(name="pet_type")//ประเภทสัตว์ ex. หมา แมว
    private String petType;
    @Column(name="breed")//พันธุ์
    private String breed;

    @Column(name="remark")//allergic
    private String remark;
    @Column(name="soft_deleted_date")
    private Date softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;

    @Lob
    @Column(name="image")
    private String image;


    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bill> items;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CureHistory> cureHistories;

    public Pet() {super();}

    public Pet(String name, String gender, Date doB, boolean sterilization, String petType, String breed){
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.petType = petType;
        this.breed = breed;
        this.remark = "";
        this.image = "";
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

    public Pet(String name, String gender, String doB, String petType, String breed, String remark){
        this.name = name;
        this.gender = gender;
        this.doB = GlobalService.convertStringToDate(doB);
        this.sterilization = false;
        this.petType = petType;
        this.breed = breed;
        this.remark = remark;
        this.softDeleted = false;
        this.softDeletedDate = null;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petID=" + petID +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", doB='" + doB + '\'' +
                ", sterilization=" + sterilization +
                ", breed='" + breed + '\'' +
                ", remark='" + remark + '\'' +
                ", softDeletedDate='" + softDeletedDate + '\'' +
                ", softDeleted=" + softDeleted +
                '}';
    }
}
