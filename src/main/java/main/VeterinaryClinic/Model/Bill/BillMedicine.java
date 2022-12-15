package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;

import javax.persistence.*;

@Entity
@Table(name="Bill_medicine")
@Data
public class BillMedicine {

    @EmbeddedId
    private BillMedicineID pairedID;

//    @ManyToOne()
//    @JoinColumn(name="pk_bill_id")
//    private Bill bill;

    @MapsId("bill_id")
    @ManyToOne
    private Bill bill;

    @MapsId("med_id")
    @ManyToOne
    private Medicine medicine;

    @Column(name="bill_med_amount")
    private int medAmount;

    public BillMedicine() {super();}

    public BillMedicine(Bill bill, Medicine medicine, int cureAmount) {
        this.pairedID = new BillMedicineID(bill.getBillID(),medicine.getMedID());
        this.bill = bill;
        this.medicine = medicine;
        this.medAmount = cureAmount;
    }

    @Override
    public String toString() {
        return "BillMedicine{" +
                "pairedID=" + pairedID +
                ", bill=" + bill +
                ", medicine=" + medicine +
                ", cureAmount=" + medAmount +
                '}';
    }
}
