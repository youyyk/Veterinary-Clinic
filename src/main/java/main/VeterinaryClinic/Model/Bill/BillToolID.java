package main.VeterinaryClinic.Model.Bill;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillToolID implements Serializable {

    @Column(name = "_id")
    protected long bill_id;

    @Column(name = "_id")
    protected long tool_id;

    public BillToolID() {super();}

    public BillToolID(long bill_id, long tool_id) {
        this.bill_id = bill_id;
        this.tool_id = tool_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillToolID billToolID = (BillToolID) o;
        return bill_id == billToolID.bill_id && tool_id == billToolID.tool_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill_id, tool_id);
    }
}
