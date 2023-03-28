package main.VeterinaryClinic.Model;

import lombok.Data;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Service.GlobalService;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Column(name="ware_type", columnDefinition = "CHAR(10)")
    private String type;
    @Column(name="paid_total") //ต้นทุน
    private double paidTotal;
    @Column(name="created_date")
    private Date createdDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name="expired_date")
    private Date expiredDate;
    @Column(name="soft_deleted_date")
    private Date softDeletedDate;
    @Column(name="soft_deleted")
    private boolean softDeleted;

    @OneToMany(mappedBy = "wareHouse", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<BillMedicine> billMed;

    @OneToMany(mappedBy = "wareHouse", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<BillTool> billTool;



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
        this.softDeleted = false;
        this.softDeletedDate = null;
    }

    public WareHouse(Tool tool, int quantityIn, double paidTotal, Date expiredDate) {
        this.tool = tool;
        this.quantityIn = quantityIn;
        this.quantityLeft = quantityIn;
        this.type = "tool";
        this.paidTotal = paidTotal;
        this.createdDate = GlobalService.getCurrentTime();
        this.expiredDate = expiredDate;
        this.softDeleted = false;
        this.softDeletedDate = null;
    }

    public void updateFieldForEdit(Medicine medicine, Tool tool, int quantityIn, int quantityLeft, double paidTotal, Date stockInDate, Date expiredDate) {
        this.medicine = medicine;
        this.tool = tool;
        this.quantityIn = quantityIn;
        this.quantityLeft = quantityLeft;
        this.type = medicine!=null ? "medicine" : "tool";
        this.paidTotal = paidTotal;
        this.createdDate = stockInDate;
        this.expiredDate = expiredDate;
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

    public boolean isCanDelete(){
        return billMed.size() == 0 && billTool.size() == 0 && quantityIn==quantityLeft ? true : false;
    }

    @Override
    public String toString() {
        return "WareHouse{" +
                "itemID=" + itemID +
                ", medicine=" + medicine +
                ", tool=" + tool +
                ", quantityIn=" + quantityIn +
                ", quantityLeft=" + quantityLeft +
                ", type='" + type + '\'' +
                ", paidTotal=" + paidTotal +
                ", createdDate=" + createdDate +
                ", expiredDate=" + expiredDate +
                ", softDeletedDate=" + softDeletedDate +
                ", softDeleted=" + softDeleted +
                ", billMed=" + billMed.size() +
                ", billTool=" + billTool.size() +
                '}';
    }
}