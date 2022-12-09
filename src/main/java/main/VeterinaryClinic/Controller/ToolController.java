package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController {
    @Autowired
    private ToolService toolService;

    @GetMapping
    public List<Tool> getAllTool() {
        return toolService.getAll();
    }


}
