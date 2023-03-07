package main.VeterinaryClinic.Service;

import lombok.Data;
import main.VeterinaryClinic.Model.Medicine;

@Data
public
class MedicineAmt {
    private Medicine medicine;
    private int amount;

    private double totalPrice;

    public MedicineAmt(Medicine medicine, int amount) {
        this.medicine = medicine;
        this.amount = amount;
        this.totalPrice = medicine.getPrice()*amount;
    }
}