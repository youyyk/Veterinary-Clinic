package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Serving;
import main.VeterinaryClinic.Model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ServingRepository extends JpaRepository<Serving, Long> {
    //    Serving findByServiceID(long serviceId);
    Serving findByServingID(long servingID);
    List<Serving> findBySoftDeleted(boolean softDeleted);
}
