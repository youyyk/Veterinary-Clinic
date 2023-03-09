package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    public List<Appointment> getAll() {
        return repository.findAll();
    }

    public Appointment save(Appointment appointment) {
        return repository.save(appointment);
    }

    public Appointment findByAppointmentID(long appointmentID) {
        return repository.findByAppointmentID(appointmentID);
    }

    public void deleteByAppointmentID(long appointmentID){repository.deleteByAppointmentID(appointmentID);}

    public List<Appointment> findByPet_Account_AccIdOrderByDateAsc(UUID accID) {
        return repository.findByPet_Account_AccIdOrderByDateAsc(accID);
    }


}
