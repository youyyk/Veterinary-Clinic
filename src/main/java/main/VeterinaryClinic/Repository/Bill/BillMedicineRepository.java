package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillMedicineID;
import main.VeterinaryClinic.Model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillMedicineRepository extends JpaRepository<BillMedicine, BillMedicineID> {
    BillMedicine findByPairedID_BillAndPairedID_Med(long billID, long medID);

    BillMedicine deleteBillMedicineByBill_BillIDAndMedicine_MedID(long billID, long medID);

}
