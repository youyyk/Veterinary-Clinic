package main.VeterinaryClinic.Repository;

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

    WareHouse findFirstByMedicineOrderByCreatedDateAsc(Medicine medicine);

//    List<WareHouse> findByMedicineAndExpiredDateBeforeOrderByCreatedDateAsc(Medicine medicine, Date expiredDate);
    List<WareHouse> findByMedicineAndSoftDeletedAndExpiredDateBeforeOrderByCreatedDateAsc(Medicine medicine,boolean softDeleted, Date expiredDate);
    List<WareHouse> findByToolAndSoftDeletedAndExpiredDateBeforeOrderByCreatedDateAsc(Tool tool,boolean softDeleted, Date expiredDate);
    WareHouse findFirstByToolOrderByCreatedDateAsc(Tool tool);
//    List<WareHouse> findByToolAndExpiredDateBeforeOrderByCreatedDateAsc(Medicine medicine, Date expiredDate);

}
