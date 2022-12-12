package main.VeterinaryClinic.Model;

import main.VeterinaryClinic.Service.ZService;

import javax.persistence.*;

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

//    private long medID = medicine.getMedID();

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
    private String expiredDate;

    public WareHouse() {
        super();
    }

    public WareHouse(int amount, String type, double paidTotal,String expiredDate) {
        this.amount = amount;
        this.type = type;
        this.paidTotal = paidTotal;
        this.createdDate = ZService.getCurrentTime();
        this.expiredDate = expiredDate;
    }

    public WareHouse(Medicine medicine, int amount, double paidTotal,String expiredDate) {
        this.medicine = medicine;
        this.amount = amount;
        this.type = "medicine";
        this.paidTotal = paidTotal;
        this.createdDate = ZService.getCurrentTime();
        this.expiredDate = expiredDate;
    }

    public WareHouse(Tool tool, int amount, double paidTotal,String expiredDate) {
        this.tool = tool;
        this.amount = amount;
        this.type = "tool";
        this.paidTotal = paidTotal;
        this.createdDate = ZService.getCurrentTime();
        this.expiredDate = expiredDate;
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

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }
}
