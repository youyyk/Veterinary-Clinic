package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Meeting;
import main.VeterinaryClinic.Repository.MedicineRepository;
import main.VeterinaryClinic.Repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository repository;

    public List<Meeting> getAll() {
        return repository.findAll();
    }

    public Meeting create(Meeting meeting) {
        return repository.save(meeting);
    }


}
