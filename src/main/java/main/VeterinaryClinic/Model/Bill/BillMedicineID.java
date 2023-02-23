package main.VeterinaryClinic.Model.Bill;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillMedicineID implements Serializable {

    @Column(name = "_id")
    protected long bill;

    @Column(name = "_id")
    protected long med;

    public BillMedicineID() {super();}

    public BillMedicineID(long bill, long med) {
        this.bill = bill;
        this.med = med;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillMedicineID billMedicineID = (BillMedicineID) o;
        return bill == billMedicineID.bill && med == billMedicineID.med;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill, med);
    }
}
