package main.VeterinaryClinic.Model.Bill;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillToolID implements Serializable {

    @Column(name = "_id")
    protected long bill;

    @Column(name = "_id")
    protected long item;

    public BillToolID() {super();}

    public BillToolID(long bill, long item) {
        this.bill = bill;
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillToolID billToolID = (BillToolID) o;
        return bill == billToolID.bill && item == billToolID.item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill, item);
    }
}
