package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.CureHistory;
import main.VeterinaryClinic.Repository.CureHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CureHistoryService {
    @Autowired
    private CureHistoryRepository repository;

    public List<CureHistory> getAll() {
        return repository.findAll();
    }

    public CureHistory create(CureHistory meeting) {
        return repository.save(meeting);
    }


}
