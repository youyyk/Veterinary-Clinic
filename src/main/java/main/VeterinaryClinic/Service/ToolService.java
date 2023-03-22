package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
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

    public Tool save(Tool tool) {
        return repository.save(tool);
    }

    public Tool findByToolID(long toolID){return repository.findByToolID(toolID);}

    public List<Tool> findBySoftDeleted(boolean want){return repository.findBySoftDeleted(want);}

    public void updateTool(Tool oldTool, Tool newTool){
        if (oldTool.getPrice() != newTool.getPrice()){
            delete(oldTool);
            repository.save(newTool);
        } else {
            oldTool.updateFieldForEdit(newTool.getName(), newTool.getPrice(), newTool.getDescription());
            repository.save(oldTool);
        }
    }
    public boolean delete(Tool tool){
        if (tool.isCanDelete()){
            repository.delete(tool);
            return true;
        }
        tool.setSoftDeleted(true);
        tool.setSoftDeletedDate(GlobalService.getCurrentTime());
        repository.save(tool);
        return false;
    }
}