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
    protected long tool;

    public BillToolID() {super();}

    public BillToolID(long bill, long tool) {
        this.bill = bill;
        this.tool = tool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillToolID billToolID = (BillToolID) o;
        return bill == billToolID.bill && tool == billToolID.tool;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill, tool);
    }
}
