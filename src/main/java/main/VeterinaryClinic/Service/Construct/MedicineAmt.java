package main.VeterinaryClinic.Service.Construct;

import lombok.Data;
import main.VeterinaryClinic.Model.Medicine;

@Data
public
class MedicineAmt {
    private Medicine medicine;
    private int amount;
    private String description;
    private double totalPrice;

    public MedicineAmt(Medicine medicine, int amount,String description) {
        this.medicine = medicine;
        this.amount = amount;
        this.description = description;
        this.totalPrice = medicine.getPrice()*amount;
    }
}