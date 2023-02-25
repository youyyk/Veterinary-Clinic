package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Repository.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseService {
    @Autowired
    private WareHouseRepository repository;

    public List<WareHouse> getAll() {
        return repository.findAll();
    }

    public WareHouse getById(long id) {
        return repository.findByItemID(id);
    }

    public WareHouse save(WareHouse wareHouse) {
        return repository.save(wareHouse);
    }
}
