package main.VeterinaryClinic.Service.SubBill;

import main.VeterinaryClinic.Model.Bill.BillService;
import main.VeterinaryClinic.Repository.Bill.BillServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceService {
    @Autowired
    private BillServiceRepository repository;

    public List<BillService> getAll() {
        return repository.findAll();
    }

    public BillService create(BillService billService) {
        return repository.save(billService);
    }


}
