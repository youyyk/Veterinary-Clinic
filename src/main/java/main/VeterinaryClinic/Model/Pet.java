package main.VeterinaryClinic.Model;

import lombok.Data;
import main.VeterinaryClinic.Model.Bill.Bill;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Pet")
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
    @Column(name="date_of_birth")
    private Date doB;
    @Column(name="sterilization")//ทำหมัน
    private boolean sterilization;
    @Column(name="pet_type")//ประเภทสัตว์ ex. หมา แมว
    private String petType;
    @Column(name="breed")//พันธุ์
    private String breed;
    @Column(name="cure_history")
    private String cureHistory;
    @Column(name="remark")//allergic
    private String remark;
    @Column(name="soft_deleted_date")
    private Date softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;


    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bill> items;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Meeting> meetings;

    public Pet() {super();}

    public Pet(String name, String gender, Date doB, boolean sterilization, String petType, String breed){
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.petType = petType;
        this.breed = breed;
        this.cureHistory = "";
        this.remark = "";
        this.softDeleted = false;
        this.softDeletedDate = null;
    }

    public Pet(String name, String gender, Date doB, boolean sterilization, String petType, String breed, String cureHistory, String remark){
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.petType = petType;
        this.breed = breed;
        this.cureHistory = cureHistory;
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
                ", cureHistory='" + cureHistory + '\'' +
                ", remark='" + remark + '\'' +
                ", softDeletedDate='" + softDeletedDate + '\'' +
                ", softDeleted=" + softDeleted +
                '}';
    }
}
