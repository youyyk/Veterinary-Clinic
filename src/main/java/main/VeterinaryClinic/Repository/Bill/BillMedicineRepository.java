package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillMedicineID;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillMedicineRepository extends JpaRepository<BillMedicine, BillMedicineID> {
//    BillMedicine findByPairedID_BillAndPairedID_Med(long billID, long medID);
//
//    BillMedicine deleteBillMedicineByBill_BillIDAndMedicine_MedID(long billID, long medID);

    List<BillMedicine> findByBill_BillID(long billID);

    List<BillMedicine> findByBill(Bill bill);

    @Transactional
    void deleteBillMedicineByBillAndWareHouse_Medicine_MedID(Bill bill, long medID);


    List<BillMedicine> findByBillAndWareHouse_Medicine(Bill bill, Medicine medicine);




}
