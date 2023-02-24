package main.VeterinaryClinic.Model.Bill;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillServingID implements Serializable {

    @Column(name = "_id")
    protected long bill;

    @Column(name = "_id")
    protected long serving;

    public BillServingID() {super();}

    public BillServingID(long bill, long service_id) {
        this.bill = bill;
        this.serving = service_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillServingID billMedicineID = (BillServingID) o;
        return bill == billMedicineID.bill && serving == billMedicineID.serving;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill, serving);
    }
}
