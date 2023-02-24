package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.Tool;

import javax.persistence.*;

@Entity
@Table(name="bill_tools")
@Data
public class BillTool {

    @EmbeddedId
    private BillToolID pairedID;

    @MapsId("bill")
    @ManyToOne
    private Bill bill;

    @MapsId("tool")
    @ManyToOne
    private Tool tool;

    @Column(name="bill_tool_total")
    private int toolTotal;

    public BillTool() {super();}

    public BillTool(Bill bill, Tool tool, int toolTotal) {
        this.pairedID = new BillToolID(bill.getBillID(),tool.getToolID());
        this.bill = bill;
        this.tool = tool;
        this.toolTotal = toolTotal;
    }

    @Override
    public String toString() {
        return "BillTool{" +
                "pairedID=" + pairedID +
                ", bill=" + bill +
                ", tool=" + tool +
                ", serviceAmount=" + toolTotal +
                '}';
    }
}
