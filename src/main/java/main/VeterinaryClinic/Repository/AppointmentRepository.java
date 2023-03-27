package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPet_Account_AccIdOrderByDateAsc(UUID accID);

    List<Appointment> findByDateBetween(Date starDate,Date endDate);

    @Transactional
    Appointment findByAppointmentID(long appointmentID);

    @Transactional
    List<Appointment> findByDate(Date date);


    @Transactional
    void deleteByAppointmentID(long appointmentID);

    @Transactional
    List<Appointment> findByDateOrderByPeriodDesc(Date date);


}
