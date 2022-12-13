package main.VeterinaryClinic.Model.Bill;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.WareHouse;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bill_id")
    private Long billID;

    @ManyToOne()
    @JoinColumn(name="pet_id")
    private Pet pet;

    @Column(name="date")
    private String date;
    @Column(name="pay_type")
    private String payType;
    @Column(name="total")
    private double total;
    @Column(name="paid_status")
    private boolean paidStatus;
    @Column(name="soft_deleted_dated")
    private String softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BillMedicine> items;

    public Bill() {super();}

    public Bill(Pet pet, String date, String payType, double total) {
        this.pet = pet;
        this.date = date;
        this.payType = payType;
        this.total = total;
        this.paidStatus = false;
        this.softDeleted = false;
        this.softDeletedDate = "";
    }

    public Long getBillID() {
        return billID;
    }

    public void setBillID(Long billID) {
        this.billID = billID;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(boolean paidStatus) {
        this.paidStatus = paidStatus;
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
        return "Bill{" +
                "BillID=" + billID +
                ", pet=" + pet +
                ", date='" + date + '\'' +
                ", payType='" + payType + '\'' +
                ", total=" + total +
                ", paidStatus=" + paidStatus +
                ", softDeletedDate='" + softDeletedDate + '\'' +
                ", softDeleted=" + softDeleted +
                '}';
    }
}
