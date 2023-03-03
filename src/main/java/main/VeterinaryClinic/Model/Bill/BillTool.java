package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Model.WareHouse;

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

    @MapsId("item")
    @ManyToOne
    private WareHouse wareHouse;

    @Column(name="bill_tool_total")
    private int toolTotal;

    public BillTool() {super();}

//    public BillTool(Bill bill, Tool tool, int toolTotal) {
//        this.pairedID = new BillToolID(bill.getBillID(),tool.getToolID());
//        this.bill = bill;
//        this.tool = tool;
//        this.toolTotal = toolTotal;
//    }


    public BillTool( Bill bill, WareHouse wareHouse, int toolTotal) {
        this.pairedID = new BillToolID(bill.getBillID(), wareHouse.getItemID());
        this.bill = bill;
        this.wareHouse = wareHouse;
        this.toolTotal = toolTotal;
    }


}
