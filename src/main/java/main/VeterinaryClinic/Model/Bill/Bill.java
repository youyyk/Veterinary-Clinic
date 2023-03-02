package main.VeterinaryClinic.Model.Bill;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Service.GlobalService;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="bills")
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bill_id")
    private Long billID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "treatment_history_id")
    private TreatmentHistory treatmentHistory;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;
    @Column(name="pay_type", columnDefinition = "CHAR(10)")
    private String payType;
    @Column(name="total")
    private double total;
    @Column(name="paid_status")
    private boolean paidStatus;

    @Column(name="receive")
    private double receive;

    @Column(name="discount")
    private double discount;


    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BillMedicine> medUsed;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BillTool> toolUsed;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BillServing> serviceUsed;

    public Bill() {super();}


    public Bill(TreatmentHistory treatmentHistory) {
//        System.out.println("----"+treatmentHistory);
        this.treatmentHistory = treatmentHistory;
        this.payType = null;
        this.paidStatus = false;
        this.startDate = GlobalService.getCurrentTime();
        this.endDate = null;
    }


    @Override
    public String toString() {
        return "Bill{" +
                "billID=" + billID +
                ", treatmentHistory=" + treatmentHistory.getTreatmentHisID() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", payType='" + payType + '\'' +
                ", total=" + total +
                ", paidStatus=" + paidStatus +
                ", receive=" + receive +
                ", discount=" + discount +
//                ", medUsed=" + medUsed +
//                ", toolUsed=" + toolUsed +
//                ", serviceUsed=" + serviceUsed +
                '}';
    }
}