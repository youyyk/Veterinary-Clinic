package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.CureHistory;
import main.VeterinaryClinic.Service.CureHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cureHistory")
public class CureHistoryController {
    @Autowired
    private CureHistoryService cureHistoryService;

    @GetMapping
    public List<CureHistory> getAllHistory() {
        return cureHistoryService.getAll();
    }

//    @GetMapping
//    public String getMeetingPage(Model model) {
//
//        // step 1. update model for template
//        model.addAttribute("cureHistories", meetingService.getAll());
//
//        // step 2. choose HTML template
//        return "meeting";
//    }


}
