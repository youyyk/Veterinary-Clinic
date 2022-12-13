package main.VeterinaryClinic.Model.Bill;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;

import javax.persistence.*;

@Entity
@Table(name="Bill_medicine")
public class BillMedicine {

    @EmbeddedId
    private BillMedicineID pairedID;

    @ManyToOne()
    @JoinColumn(name="pk_bill_id")
    private Bill bill;

    @ManyToOne()
    @JoinColumn(name="pk_med_id")
    private Medicine medicine;

    @Column(name="cure_amount")
    private double cureAmount;

    public BillMedicine() {super();}

    public BillMedicine(Bill bill, Medicine medicine, double cureAmount) {
        this.pairedID = new BillMedicineID(bill.getBillID(),medicine.getMedID());
        this.bill = bill;
        this.medicine = medicine;
        this.cureAmount = cureAmount;
    }

    public BillMedicineID getPairedID() {
        return pairedID;
    }

    public void setPairedID(BillMedicineID pairedID) {
        this.pairedID = pairedID;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public double getCureAmount() {
        return cureAmount;
    }

    public void setCureAmount(double cureAmount) {
        this.cureAmount = cureAmount;
    }

    @Override
    public String toString() {
        return "BillMedicine{" +
                "pairedID=" + pairedID +
                ", bill=" + bill +
                ", medicine=" + medicine +
                ", cureAmount=" + cureAmount +
                '}';
    }
}
