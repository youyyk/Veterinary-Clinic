package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Model.Bill.BillServingID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BillServiceRepository extends JpaRepository<BillServing, BillServingID> {
    BillServing findByPairedID_BillAndPairedID_Serving(long billID, long tooID);

    @Transactional
    void deleteBillServingByBill_BillIDAndServing_ServingID(long billID, long servingID);
}
