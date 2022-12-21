package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meeting")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

//    @GetMapping
//    public List<Meeting> getAllMeeting() {
//        return meetingService.getAll();
//    }

    @GetMapping
    public String getMeetingPage(Model model) {

        // step 1. update model for template
        model.addAttribute("meetings", appointmentService.getAll());

        // step 2. choose HTML template
        return "meeting";
    }


}
