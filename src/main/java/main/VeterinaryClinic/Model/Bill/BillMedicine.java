package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.WareHouse;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="bill_medicines")
@Data
public class BillMedicine {

    @EmbeddedId
    private BillMedicineID pairedID;

    @MapsId("bill")
    @ManyToOne
    private Bill bill;

//    @MapsId("med")
//    @ManyToOne
//    private Medicine medicine;
    @MapsId("item")
    @ManyToOne
    private WareHouse wareHouse;

    @Column(name="new_description")
    private String newDescription;

    @Column(name="bill_med_total")
    private int medTotal;


    public BillMedicine() {super();}

//    public BillMedicine(Bill bill, Medicine medicine, int cureAmount) {
//        this.pairedID = new BillMedicineID(bill.getBillID(),medicine.getMedID());
//        this.bill = bill;
//        this.medicine = medicine;
//        this.medTotal = cureAmount;
//    }


    public BillMedicine(Bill bill, WareHouse wareHouse, String newDescription, int medTotal) {
        this.pairedID = new BillMedicineID(bill.getBillID(), wareHouse.getItemID());
        this.bill = bill;
        this.wareHouse = wareHouse;
        this.newDescription = newDescription;
        this.medTotal = medTotal;
    }
    public BillMedicine(Bill bill, WareHouse wareHouse, int medTotal) {
        this.pairedID = new BillMedicineID(bill.getBillID(), wareHouse.getItemID());
        this.bill = bill;
        this.wareHouse = wareHouse;
        this.medTotal = medTotal;
    }
}
