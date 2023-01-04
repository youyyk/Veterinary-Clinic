package main.VeterinaryClinic.Model.Bill;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillServingID implements Serializable {

    @Column(name = "_id")
    protected long bill_id;

    @Column(name = "_id")
    protected long serving_id;

    public BillServingID() {super();}

    public BillServingID(long bill_id, long service_id) {
        this.bill_id = bill_id;
        this.serving_id = service_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillServingID billMedicineID = (BillServingID) o;
        return bill_id == billMedicineID.bill_id && serving_id == billMedicineID.serving_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill_id, serving_id);
    }
}
