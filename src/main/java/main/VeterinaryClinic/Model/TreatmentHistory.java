package main.VeterinaryClinic.Model;

import lombok.Data;
import main.VeterinaryClinic.Model.Bill.Bill;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="treatment_histories")
@Data
public class TreatmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="treatment_history_id")
    private Long treatmentHisID;

    @ManyToOne()
    @JoinColumn(name="pet_id")
    private Pet pet;


//    @OneToOne(cascade = CascadeType.ALL,optional = false)
//    @JoinColumn(name = "bill_id")
//    private Bill bill;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "treatmentHistory")
    private Bill bill;


    @Column(name="treat_date")
    private Date date;

    @Column(name="diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name="weight")
    private double weight;

    @Column(name="our_clinic")
    private boolean ourClinic;

    public TreatmentHistory() {super();}

    public TreatmentHistory(Bill bill) {
        this.bill = bill;
    }


    public TreatmentHistory(Pet pet, Date date, String diagnosis, double weight, boolean ourClinic) {
        this.pet = pet;
        this.date = date;
        this.diagnosis = diagnosis;
        this.weight = weight;
        this.ourClinic = ourClinic;
    }

    public TreatmentHistory(Pet pet, Date date, double weight, boolean ourClinic) {
        this.pet = pet;
        this.date = date;
        this.weight = weight;
        this.ourClinic = ourClinic;
    }

    public TreatmentHistory(Pet pet, Date date, String diagnosis, boolean ourClinic) {
        this.pet = pet;
        this.date = date;
        this.diagnosis = diagnosis;
        this.weight = -1;
        this.ourClinic = ourClinic;
    }


    @Override
    public String toString() {
        return "TreatmentHistory{" +
                "treatmentHisID=" + treatmentHisID +
                ", pet=" + pet.getName() +
                ", bill=" + bill.getBillID() +
                ", date=" + date +
                ", diagnosis='" + diagnosis + '\'' +
                ", weight=" + weight +
                ", ourClinic=" + ourClinic +
                '}';
    }
}
