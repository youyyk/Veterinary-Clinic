package main.VeterinaryClinic.Model;

import main.VeterinaryClinic.Service.ZService;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="Warehouse")
public class WareHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long itemID;

    @ManyToOne()
    @JoinColumn(name="med_id")
    private Medicine medicine;

    @ManyToOne()
    @JoinColumn(name="tool_id")
    private Tool tool;

    @Column(name="ware_amount")
    private int amount;

    @Column(name="ware_type")
    private String type;
    @Column(name="paid_total") //ต้นทุน
    private double paidTotal;
    @Column(name="created_date")
    private String createdDate;
    @Column(name="expired_date")
    private String deletedDate;

    public WareHouse() {
        super();
    }

    public WareHouse(int amount, String type, double paidTotal,String deletedDate) {
        this.amount = amount;
        this.type = type;
        this.paidTotal = paidTotal;
        this.createdDate = ZService.getCurrentTime();
        this.deletedDate = deletedDate;
    }

    public WareHouse(Medicine medicine, int amount, String type, double paidTotal,String deletedDate) {
        this.medicine = medicine;
        this.amount = amount;
        this.type = type;
        this.paidTotal = paidTotal;
        this.createdDate = ZService.getCurrentTime();
        this.deletedDate = deletedDate;
    }

    public WareHouse(Tool tool, int amount, String type, double paidTotal,String deletedDate) {
        this.tool = tool;
        this.amount = amount;
        this.type = type;
        this.paidTotal = paidTotal;
        this.createdDate = ZService.getCurrentTime();
        this.deletedDate = deletedDate;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(double paidTotal) {
        this.paidTotal = paidTotal;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(String deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}
