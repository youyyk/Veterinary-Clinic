package main.VeterinaryClinic.Model;

import lombok.Data;
import main.VeterinaryClinic.Service.GlobalService;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Warehouse")
@Data
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
    private Date createdDate;
    @Column(name="expired_date")
    private Date expiredDate;

    public WareHouse() {
        super();
    }

    public WareHouse(int amount, String type, double paidTotal,Date expiredDate) {
        this.amount = amount;
        this.type = type;
        this.paidTotal = paidTotal;
        this.createdDate = GlobalService.getCurrentTime();
        this.expiredDate = expiredDate;
    }

    public WareHouse(Medicine medicine, int amount, double paidTotal,Date expiredDate) {
        this.medicine = medicine;
        this.amount = amount;
        this.type = "medicine";
        this.paidTotal = paidTotal;
        this.createdDate = GlobalService.getCurrentTime();
        this.expiredDate = expiredDate;
    }

    public WareHouse(Tool tool, int amount, double paidTotal,Date expiredDate) {
        this.tool = tool;
        this.amount = amount;
        this.type = "tool";
        this.paidTotal = paidTotal;
        this.createdDate = GlobalService.getCurrentTime();
        this.expiredDate = expiredDate;
    }

}
