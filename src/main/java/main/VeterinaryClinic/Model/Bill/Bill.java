package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Service.GlobalService;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Bill")
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bill_id")
    private Long billID;

    @ManyToOne()
    @JoinColumn(name="pet_id")
    private Pet pet;
    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;
    @Column(name="pay_type")
    private String payType;
    @Column(name="total")
    private double total;
    @Column(name="paid_status")
    private boolean paidStatus;

    @Column(name="receive")
    private double receive;

    @Column(name="discount")
    private double discount;

    @Column(name="diagnosis")
    private String diagnosis;



    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BillMedicine> medUsed;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BillTool> toolUsed;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BillService> serviceUsed;

    public Bill() {super();}

    public Bill(Pet pet) {
        this.pet = pet;
        this.payType = null;
        this.paidStatus = false;
        this.startDate = GlobalService.getCurrentTime();
        this.endDate = null;
    }

}