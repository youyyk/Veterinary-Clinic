package main.VeterinaryClinic.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="servings")
@Data
public class Serving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="serving_id")
    private Long serviceID;
    @Column(name="serving_name")
    private String name;
    @Column(name="price")
    private double price;
    @Column(name="soft_deleted_date")
    private String softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;


//    @OneToMany(mappedBy = "medicine", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private List<WareHouse> items;


    public Serving() { super();}

    public Serving(String name, double price) {
        this.name = name;
        this.price = price;
        this.softDeleted = false;
        this.softDeletedDate = "";
    }


    @Override
    public String toString() {
        return "Serving{" +
                "serviceID=" + serviceID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", softDeletedDate='" + softDeletedDate + '\'' +
                ", softDeleted=" + softDeleted +
                '}';
    }
}