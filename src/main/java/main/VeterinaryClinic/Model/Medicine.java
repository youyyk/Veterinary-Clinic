package main.VeterinaryClinic.Model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="Medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="med_id")
    private Long medID;

    @Column(name="med_name")
    private String name;
    @Column(name="dose")
    private String dose;
    @Column(name="unit")
    private String unit;
    @Column(name="price")
    private double price;
    @Column(name="description")
    private String description;
    @Column(name="soft_deleted_dated")
    private String softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;


    @OneToMany(mappedBy = "medicine", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<WareHouse> items;


    public Medicine() {
        super();
    }

    public Medicine(String name, String unit, double price, String description) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.description = description;
        this.softDeleted = false;
        this.softDeletedDate = "";
        this.dose = "";
    }

    public Medicine(String name, String unit, double price, String description, String dose) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.description = description;
        this.dose = dose;
        this.softDeleted = false;
        this.softDeletedDate = "";
    }

    public Long getMedID() {
        return medID;
    }

    public void setMedID(Long medID) {
        this.medID = medID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
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
}
