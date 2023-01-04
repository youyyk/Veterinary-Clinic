package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Serving;
import main.VeterinaryClinic.Repository.ServingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServingService {
    @Autowired
    private ServingRepository repository;

    public List<Serving> getAll() {
        return repository.findAll();
    }

    public Serving create(Serving serving) {
        return repository.save(serving);
    }


}