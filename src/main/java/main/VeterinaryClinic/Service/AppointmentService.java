package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;


    public List<Appointment> getAll() {
        Sort sort = Sort.by("date").ascending().and(Sort.by("period").descending());
        return repository.findAll(sort);
    }

    public List<Appointment> findByDateBetween(Date startDate,Date endDate) {
        return repository.findByDateBetween(startDate,endDate);
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

    public List<Appointment> findByDateGreaterThanTodayDateEqualOrderByDateAsc() {
        return repository.findByDateGreaterThanEqualOrderByDateAsc(GlobalService.getDefaultTodayDateZeroTime(0));
    }

    public List<Appointment> findByDateLessThanTodayDateOrderByDateDesc() {
        return repository.findByDateLessThanOrderByDateDesc(GlobalService.getDefaultTodayDateZeroTime(0));
    }

    public List<Appointment> getAllAppointmentListForTable() {
        List<Appointment> appointmentList = findByDateGreaterThanTodayDateEqualOrderByDateAsc();
        appointmentList.addAll(findByDateLessThanTodayDateOrderByDateDesc());
        return appointmentList;
    }

    public List<Appointment> getAllAppointmentListForTableOnAccount(UUID accId) {
        List<Appointment> appointmentList = repository.findByPet_Account_AccIdAndDateGreaterThanEqualOrderByDateAsc(accId, GlobalService.getDefaultTodayDateZeroTime(0));
        appointmentList.addAll(repository.findByPet_Account_AccIdAndDateLessThanOrderByDateDesc(accId, GlobalService.getDefaultTodayDateZeroTime(0)));
        return appointmentList;
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
