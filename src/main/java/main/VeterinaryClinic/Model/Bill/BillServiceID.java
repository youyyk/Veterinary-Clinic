package main.VeterinaryClinic.Model.Bill;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillServiceID implements Serializable {

    @Column(name = "_id")
    protected long bill_id;

    @Column(name = "_id")
    protected long service_id;

    public BillServiceID() {super();}

    public BillServiceID(long bill_id, long service_id) {
        this.bill_id = bill_id;
        this.service_id = service_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillServiceID billMedicineID = (BillServiceID) o;
        return bill_id == billMedicineID.bill_id && service_id == billMedicineID.service_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill_id, service_id);
    }
}
