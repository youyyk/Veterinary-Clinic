package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
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

    public List<Appointment> findByDate(Date date) {
        return repository.findByDate(date);
    }

    public void deleteByAppointmentID(long appointmentID){repository.deleteByAppointmentID(appointmentID);}

    public List<Appointment> findByPet_Account_AccIdOrderByDateAsc(UUID accID) {
        return repository.findByPet_Account_AccIdOrderByDateAsc(accID);
    }

    public List<Appointment> findByTodayDateOrderByPeriodDesc() {
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(new Date());
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        Date date = calStart.getTime();
        System.out.println("today (find by) : "+date);
        return repository.findByDateOrderByPeriodDesc(date);
    }





}
