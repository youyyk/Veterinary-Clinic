package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Model.Bill.BillToolID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillToolRepository extends JpaRepository<BillTool, BillToolID> {
//   BillTool findByBill_idAndTool_id(long billID, long tooID);

}
