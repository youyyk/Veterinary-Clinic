package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Model.Bill.BillToolID;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillToolRepository extends JpaRepository<BillTool, BillToolID> {
//   BillTool findByPairedID_BillAndPairedID_Tool(long billID, long tooID);
    List<BillTool> findByBill(Bill bill);

    List<BillTool> findByBillAndWareHouse_Tool(Bill bill, Tool tool);

    @Transactional
    void deleteBillToolByBillAndWareHouse_Tool_ToolID(Bill bill, long toolID);

}
