package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.AppointmentService;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PetService petService;

//    @GetMapping
//    public List<Meeting> getAllMeeting() {
//        return meetingService.getAll();
//    }

    @GetMapping
    public String getMeetingPage(Model model) {

        // step 1. update model for template
        model.addAttribute("appointments", appointmentService.getAll());

        // step 2. choose HTML template
        return "appointment/appointment";
    }

    @RequestMapping(path = "/create", method = POST)
    public String createAppointment(@RequestParam("petID") long petID,
                            @RequestParam("appDate")String appDate,
                            @RequestParam("period") String period,
                            @RequestParam("description") String description){
        System.out.println("---- Create Appointment ----");

        Pet pet = petService.findByPetID(petID);

        if (!(description == null) || !description.isBlank() || !description.isEmpty()){
            Appointment appointment = new Appointment(pet,GlobalService.convertStringToDate(appDate),period,description);
            appointmentService.save(appointment);
        }
        else {
            Appointment appointment = new Appointment(pet,GlobalService.convertStringToDate(appDate),period);
            appointmentService.save(appointment);
        }

        return "redirect:/account/getInfo/"+pet.getAccount().getAccId();
    }

    @RequestMapping(path = "/edit", method = POST)
    public String editAppointment(@RequestParam("appID") long appID,
                                  @RequestParam("page") String page,
                            @RequestParam("appDate")String appDate,
                            @RequestParam("period") String period,
                            @RequestParam("description") String description){
        System.out.println("---- Edit Appointment ----");

        Appointment appointment = appointmentService.findByAppointmentID(appID);
        appointment.setDate(GlobalService.convertStringToDate(appDate));
        appointment.setPeriod(period);
        appointment.setDescription(description);

        System.out.println(appointment);

        appointmentService.save(appointment);

        if (page.equals("account")){
            return "redirect:/account/getInfo/"+appointment.getPet().getAccount().getAccId();
        }
        else if (page.equals("appointment")) {
            return "redirect:/appointment";
        }

        return null;
    }

    @RequestMapping(path = "/delete", method = POST)
    public String deletePet(@RequestParam("id") String id,
                            @RequestParam("pathId") String pathId){
        System.out.println("---- Delete Appointment ----");

//        System.out.println(id.substring(1));

        long appID = Long.parseLong(id.substring(1));
        Appointment appointment = appointmentService.findByAppointmentID(appID);
        System.out.println(appointment);

        appointmentService.deleteByAppointmentID(appID);

        if (pathId.equals("appointment")){
            return "redirect:/appointment";
        }

        return "redirect:/account/getInfo/"+pathId;
    }


}
