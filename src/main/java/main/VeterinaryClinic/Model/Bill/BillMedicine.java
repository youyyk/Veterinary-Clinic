package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.Medicine;

import javax.persistence.*;

@Entity
@Table(name="bill_medicines")
@Data
public class BillMedicine {

    @EmbeddedId
    private BillMedicineID pairedID;

    @MapsId("bill")
    @ManyToOne
    private Bill bill;

    @MapsId("med")
    @ManyToOne
    private Medicine medicine;

    @Column(name="bill_med_total")
    private int medTotal;

    public BillMedicine() {super();}

    public BillMedicine(Bill bill, Medicine medicine, int cureAmount) {
        this.pairedID = new BillMedicineID(bill.getBillID(),medicine.getMedID());
        this.bill = bill;
        this.medicine = medicine;
        this.medTotal = cureAmount;
    }

    @Override
    public String toString() {
        return "BillMedicine{" +
                "pairedID=" + pairedID +
                ", bill=" + bill +
                ", medicine=" + medicine +
                ", cureAmount=" + medTotal +
                '}';
    }
}
