package main.VeterinaryClinic.Model;

import lombok.Data;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="medicines")
@Data
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="med_id")
    private Long medID;

    @Column(name="name", columnDefinition = "CHAR(30)")
    private String name;
    @Column(name="dose", columnDefinition = "CHAR(30)")
    private String dose;
    @Column(name="unit", columnDefinition = "CHAR(15)")
    private String unit;
    @Column(name="price")
    private double price;
    @Column(name="description", columnDefinition = "TEXT")
    private String description;
    @Column(name="soft_deleted_date")
    private Date softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;


    @OneToMany(mappedBy = "medicine", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<WareHouse> items;

    @OneToMany(mappedBy = "medicine", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<BillMedicine> billMed;


    public Medicine() {
        super();
    }

    public Medicine(String name, String unit, double price, String description) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.description = description;
        this.softDeleted = false;
        this.softDeletedDate = null;
        this.dose = "";
    }

    public Medicine(String name, String unit, double price, String description, String dose) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.description = description;
        this.dose = dose;
        this.softDeleted = false;
        this.softDeletedDate = null;
    }

    public void updateFieldForEdit(String name, String unit, double price, String description, String dose){
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.description = description;
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medID=" + medID +
                ", name='" + name + '\'' +
                ", dose='" + dose + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", softDeletedDate='" + softDeletedDate + '\'' +
                ", softDeleted=" + softDeleted +
                ", items=" + items +
                '}';
    }
}
