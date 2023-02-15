package main.VeterinaryClinic.Model;

import lombok.Data;
import main.VeterinaryClinic.Service.GlobalService;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="warehouse")
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

    @Column(name="quantity_in") //stock in
    private int quantityIn;

    @Column(name="quantity_left")
    private int quantityLeft;

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


    public WareHouse(Medicine medicine, int quantityIn, double paidTotal, Date expiredDate) {
        this.medicine = medicine;
        this.quantityIn = quantityIn;
        this.quantityLeft = quantityIn;
        this.type = "medicine";
        this.paidTotal = paidTotal;
        this.createdDate = GlobalService.getCurrentTime();
        this.expiredDate = expiredDate;
    }

    public WareHouse(Tool tool, int quantityIn, double paidTotal, Date expiredDate) {
        this.tool = tool;
        this.quantityIn = quantityIn;
        this.quantityLeft = quantityIn;
        this.type = "tool";
        this.paidTotal = paidTotal;
        this.createdDate = GlobalService.getCurrentTime();
        this.expiredDate = expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = GlobalService.convertStringToDate(expiredDate);
    }

    public short isExpired(){
        Date today = GlobalService.getCurrentTime();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 90);
        if (expiredDate.compareTo(today) < 0) {
            return -1; // Expired
        } else if (today.compareTo(expiredDate) * expiredDate.compareTo(c.getTime()) >= 0) {
            return 1; // Almost
        }
        return  0; // Normal
    }

}
