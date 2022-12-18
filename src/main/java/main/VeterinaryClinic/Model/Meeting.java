package main.VeterinaryClinic.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Meeting")
@Data
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="meeting_id")
    private Long meetID;

    @ManyToOne()
    @JoinColumn(name="pet_id")
    private Pet pet;

    @Column(name="meeting_date")
    private Date date;

    @Column(name="description")
    private String description;

    public Meeting(Pet pet, Date date, String description) {
        this.pet = pet;
        this.date = date;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetID=" + meetID +
                ", pet=" + pet +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
