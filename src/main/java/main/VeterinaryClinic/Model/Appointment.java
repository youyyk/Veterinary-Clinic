package main.VeterinaryClinic.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="appointments")
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

    @Column(name="period", columnDefinition = "CHAR(10)")
    private String period;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status")
    private boolean status;
    public Appointment() {super();}

    public Appointment(Pet pet, Date date, String period, String description) {
        this.pet = pet;
        this.date = date;
        this.period = period;
        this.description = description;
        this.status = false;
    }

    public Appointment(Pet pet, Date date, String period) {
        this.pet = pet;
        this.date = date;
        this.period = period;
        this.status = false;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", pet=" + pet +
                ", date=" + date +
                ", period='" + period + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
