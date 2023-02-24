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

    @MapsId("bill")
    @ManyToOne
    private Bill bill;

    @MapsId("serving")
    @ManyToOne
    private Serving serving;

    @Column(name="bill_serving_total")
    private int servingTotal;

    public BillServing() {super();}

    public BillServing(Bill bill, Serving serving, int toolAmount) {
        this.pairedID = new BillServingID(bill.getBillID(), serving.getServingID());
        this.bill = bill;
        this.serving = serving;
        this.servingTotal = toolAmount;
    }


    @Override
    public String toString() {
        return "BillService{" +
                "pairedID=" + pairedID +
                ", bill=" + bill +
                ", service=" + serving +
                ", toolAmount=" + servingTotal +
                '}';
    }
}
