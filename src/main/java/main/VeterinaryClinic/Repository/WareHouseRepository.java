package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
    List<WareHouse> findByMedicine(Medicine medicine);
    List<WareHouse> findByTool(Tool tool);

    WareHouse findByItemID(long itemID);

    List<WareHouse> getAllBySoftDeletedIsFalseOrderByExpiredDateAsc();

    List<WareHouse> findByMedicineAndSoftDeletedAndExpiredDateBeforeOrderByExpiredDateAsc(Medicine medicine,boolean softDeleted, Date expiredDate);

    List<WareHouse> findByMedicineAndSoftDeletedAndExpiredDateAfterOrderByExpiredDateAsc(Medicine medicine,boolean softDeleted, Date expiredDate);

    List<WareHouse> findByToolAndSoftDeletedAndExpiredDateBeforeOrderByExpiredDateAsc(Tool tool,boolean softDeleted, Date expiredDate);
    List<WareHouse> findByToolAndSoftDeletedAndExpiredDateAfterOrderByExpiredDateAsc(Tool tool,boolean softDeleted, Date expiredDate);
    List<WareHouse> findBySoftDeletedOrderByExpiredDateAsc(boolean softDeleted);
    List<WareHouse> findBySoftDeletedOrderByExpiredDateDesc(boolean softDeleted);







}
