package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.Serving;

import javax.persistence.*;

@Entity
@Table(name="bill_servings")
@Data
public class BillServing {

    @EmbeddedId
    private BillServingID pairedID;

    @MapsId("bill_id")
    @ManyToOne
    private Bill bill;

    @MapsId("serving_id")
    @ManyToOne
    private Serving serving;

    @Column(name="bill_tool_amount")
    private int toolAmount;

    public BillServing() {super();}

    public BillServing(Bill bill, Serving serving, int toolAmount) {
        this.pairedID = new BillServingID(bill.getBillID(), serving.getServiceID());
        this.bill = bill;
        this.serving = serving;
        this.toolAmount = toolAmount;
    }


    @Override
    public String toString() {
        return "BillService{" +
                "pairedID=" + pairedID +
                ", bill=" + bill +
                ", service=" + serving +
                ", toolAmount=" + toolAmount +
                '}';
    }
}
