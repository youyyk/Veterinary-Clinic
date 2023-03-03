package main.VeterinaryClinic.Model.Bill;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillMedicineID implements Serializable {

    @Column(name = "_id")
    protected long bill;

//    @Column(name = "_id")
//    protected long med;
    @Column(name = "_id")
    protected long item;


public BillMedicineID() {super();}

    public BillMedicineID(long bill, long item) {
        this.bill = bill;
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillMedicineID billMedicineID = (BillMedicineID) o;
        return bill == billMedicineID.bill && item == billMedicineID.item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill, item);
    }
}
