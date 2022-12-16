package main.VeterinaryClinic.Model.Bill;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillMedicineID implements Serializable {

    @Column(name = "_id")
    protected long bill_id;

    @Column(name = "_id")
    protected long med_id;

    public BillMedicineID() {super();}

    public BillMedicineID(long bill_id, long med_id) {
        this.bill_id = bill_id;
        this.med_id = med_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillMedicineID billMedicineID = (BillMedicineID) o;
        return bill_id == billMedicineID.bill_id && med_id == billMedicineID.med_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill_id, med_id);
    }
}
