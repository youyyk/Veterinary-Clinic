package main.VeterinaryClinic.Model;

import main.VeterinaryClinic.Model.Bill.Bill;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Pet")
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
    private String doB;
    @Column(name="sterilization")//ทำหมัน
    private boolean sterilization;
    @Column(name="type")//พันธุ์
    private String type;
    @Column(name="breed")//พันธุ์
    private String breed;
    @Column(name="cure_history")
    private String cureHistory;
    @Column(name="remark")//allergic
    private String remark;
    @Column(name="soft_deleted_dated")
    private String softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;


    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bill> items;


    public Pet() {super();}

    public Pet(String name, String gender, String doB, boolean sterilization, String type, String breed) {
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.type = type;
        this.breed = breed;
        this.cureHistory = "";
        this.remark = "";
        this.softDeleted = false;
        this.softDeletedDate = "";
    }

    public Pet(String name, String gender, String doB, boolean sterilization, String type, String breed, String cureHistory, String remark) {
        this.name = name;
        this.gender = gender;
        this.doB = doB;
        this.sterilization = sterilization;
        this.type = type;
        this.breed = breed;
        this.cureHistory = cureHistory;
        this.remark = remark;
        this.softDeleted = false;
        this.softDeletedDate = "";
    }



    public Long getPetID() {
        return petID;
    }

    public void setPetID(Long petID) {
        this.petID = petID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDoB() {
        return doB;
    }

    public void setDoB(String doB) {
        this.doB = doB;
    }

    public boolean isSterilization() {
        return sterilization;
    }

    public void setSterilization(boolean sterilization) {
        this.sterilization = sterilization;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCureHistory() {
        return cureHistory;
    }

    public void setCureHistory(String cureHistory) {
        this.cureHistory = cureHistory;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSoftDeletedDate() {
        return softDeletedDate;
    }

    public void setSoftDeletedDate(String softDeletedDate) {
        this.softDeletedDate = softDeletedDate;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
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
