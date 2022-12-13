package main.VeterinaryClinic.Model;

import javax.persistence.*;

@Entity
@Table(name="Service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="service_id")
    private Long serviceID;
    @Column(name="service_name")
    private String name;
    @Column(name="price")
    private double price;
    @Column(name="soft_deleted_dated")
    private String softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;


//    @OneToMany(mappedBy = "medicine", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private List<WareHouse> items;


    public Service() { super();}

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
        this.softDeleted = false;
        this.softDeletedDate = "";
    }

    public Long getServiceID() {
        return serviceID;
    }

    public void setServiceID(Long serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        return "Service{" +
                "serviceID=" + serviceID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", softDeletedDate='" + softDeletedDate + '\'' +
                ", softDeleted=" + softDeleted +
                '}';
    }
}
