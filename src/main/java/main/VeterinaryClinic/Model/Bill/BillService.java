package main.VeterinaryClinic.Model.Bill;

import lombok.Data;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Service;

import javax.persistence.*;

@Entity
@Table(name="Bill_service")
@Data
public class BillService {

    @EmbeddedId
    private BillServiceID pairedID;

    @MapsId("bill_id")
    @ManyToOne
    private Bill bill;

    @MapsId("service_id")
    @ManyToOne
    private Service service;

    @Column(name="bill_tool_amount")
    private int toolAmount;

    public BillService() {super();}

    public BillService(Bill bill, Service service, int toolAmount) {
        this.pairedID = new BillServiceID(bill.getBillID(),service.getServiceID());
        this.bill = bill;
        this.service = service;
        this.toolAmount = toolAmount;
    }


    @Override
    public String toString() {
        return "BillService{" +
                "pairedID=" + pairedID +
                ", bill=" + bill +
                ", service=" + service +
                ", toolAmount=" + toolAmount +
                '}';
    }
}
