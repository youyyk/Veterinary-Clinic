package main.VeterinaryClinic.Repository.Bill;

import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Model.Bill.BillServingID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillServiceRepository extends JpaRepository<BillServing, BillServingID> {
}
