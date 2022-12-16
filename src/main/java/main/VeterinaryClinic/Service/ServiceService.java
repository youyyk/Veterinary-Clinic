package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Service;
import main.VeterinaryClinic.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    private ServiceRepository repository;

    public List<Service> getAll() {
        return repository.findAll();
    }

    public Service create(Service service) {
        return repository.save(service);
    }


}