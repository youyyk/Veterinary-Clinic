package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillMedicineID;
import main.VeterinaryClinic.Model.Bill.BillService;
import main.VeterinaryClinic.Model.Bill.BillServiceID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillServiceRepository extends JpaRepository<BillService, BillServiceID> {
}
