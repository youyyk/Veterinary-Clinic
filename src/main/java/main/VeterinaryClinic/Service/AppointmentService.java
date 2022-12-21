package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    public List<Appointment> getAll() {
        return repository.findAll();
    }

    public Appointment create(Appointment appointment) {
        return repository.save(appointment);
    }


}
