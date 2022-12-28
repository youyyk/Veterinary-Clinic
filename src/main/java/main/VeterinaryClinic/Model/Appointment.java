package main.VeterinaryClinic.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Appointment")
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private Long appointmentID;

    @ManyToOne()
    @JoinColumn(name="pet_id")
    private Pet pet;

    @Column(name="appointment_date")
    private Date date;

    @Column(name="period")
    private String period;

    @Column(name="description")
    private String description;

    public Appointment() {super();}

    public Appointment(Pet pet, Date date, String period, String description) {
        this.pet = pet;
        this.date = date;
        this.period = period;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", pet=" + pet +
                ", date=" + date +
                ", period='" + period + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
