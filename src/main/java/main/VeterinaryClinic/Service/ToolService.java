package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Repository.MedicineRepository;
import main.VeterinaryClinic.Repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {
    @Autowired
    private ToolRepository repository;

    public List<Tool> getAll() {
        return repository.findAll();
    }

    public Tool create(Tool tool) {
        return repository.save(tool);
    }


}
