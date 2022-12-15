package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;

import javax.persistence.*;

@Entity
@Table(name="Bill_Tool")
@Data
public class BillTool {

    @EmbeddedId
    private BillToolID pairedID;

    @MapsId("bill_id")
    @ManyToOne
    private Bill bill;

    @MapsId("tool_id")
    @ManyToOne
    private Tool tool;

    @Column(name="Bill_service_amount")
    private double serviceAmount;

    public BillTool() {super();}

    public BillTool(Bill bill, Tool tool, double serviceAmount) {
        this.pairedID = new BillToolID(bill.getBillID(),tool.getToolID());
        this.bill = bill;
        this.tool = tool;
        this.serviceAmount = serviceAmount;
    }

    @Override
    public String toString() {
        return "BillTool{" +
                "pairedID=" + pairedID +
                ", bill=" + bill +
                ", tool=" + tool +
                ", serviceAmount=" + serviceAmount +
                '}';
    }
}
