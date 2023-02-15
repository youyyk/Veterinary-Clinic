package main.VeterinaryClinic.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cure_histories")
@Data
public class CureHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cure_history_id")
    private Long cureHisID;

    @ManyToOne()
    @JoinColumn(name="pet_id")
    private Pet pet;

    @Column(name="cure_date")
    private Date date;

    @Column(name="diagnosis")
    private String diagnosis;

    @Column(name="our_clinic")
    private boolean ourClinic;

    public CureHistory() {super();}

    public CureHistory(Pet pet, Date date, String diagnosis, boolean ourClinic) {
        this.pet = pet;
        this.date = date;
        this.diagnosis = diagnosis;
        this.ourClinic = ourClinic;
    }

}