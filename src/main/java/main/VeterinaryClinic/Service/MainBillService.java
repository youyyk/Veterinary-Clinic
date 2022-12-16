package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Repository.Bill.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainBillService {
    @Autowired
    private BillRepository repository;

    public List<Bill> getAll() {
        return repository.findAll();
    }

    public Bill create(Bill bill) {
        return repository.save(bill);
    }


}
