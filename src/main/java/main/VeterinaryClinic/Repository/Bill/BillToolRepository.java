package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.BillService;
import main.VeterinaryClinic.Model.Bill.BillServiceID;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Model.Bill.BillToolID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillToolRepository extends JpaRepository<BillTool, BillToolID> {
}
