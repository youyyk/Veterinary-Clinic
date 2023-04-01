package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.AppointmentService;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PetService petService;
    @Autowired
    private AccountService accountService;

//    @GetMapping
//    public List<Meeting> getAllMeeting() {
//        return meetingService.getAll();
//    }

    @GetMapping
    public String getAppointmentPage(@AuthenticationPrincipal AccountUserDetail accountUserDetail, Model model) {
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            if (!accountService.getById(accountUserDetail.getAccount().getAccId()).isRegisAccount()) {
                return "redirect:/account/register";
            }
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
        }
        System.out.println("-------- All Appointment -------");
        // step 1. update model for template
        model.addAttribute("appointments", appointmentService.getAllAppointmentListForTable());
        model.addAttribute("todaySize",appointmentService.findByTodayDateOrderByPeriodDesc().size());
        model.addAttribute("dateRange","");

        // step 2. choose HTML template
        return "appointment/appointment";
    }

    @GetMapping("/today")
    public String getAppointmentToday(@AuthenticationPrincipal AccountUserDetail accountUserDetail,Model model) {
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            if (!accountService.getById(accountUserDetail.getAccount().getAccId()).isRegisAccount()) {
                return "redirect:/account/register";
            }
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
        }
        System.out.println("--------- Today Appointment ---------");
        List<Appointment> todayList = appointmentService.findByTodayDateOrderByPeriodDesc();
        System.out.println(GlobalService.convertStringToDateSlash(GlobalService.getCurrentTime()));
        model.addAttribute("appointments",todayList );
        model.addAttribute("todaySize",todayList.size());
        model.addAttribute("dateRange",GlobalService.convertStringToDateSlash(GlobalService.getCurrentTime()) + "-" + GlobalService.convertStringToDateSlash(GlobalService.getCurrentTime()));

        return "appointment/appointment";
    }

    @GetMapping("/{startDate}to{endDate}")
    public String getAppointmentAtDateRange(@PathVariable String startDate,
                                            @PathVariable String endDate,
                                            @AuthenticationPrincipal AccountUserDetail accountUserDetail,
                                            Model model) {
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            if (!accountService.getById(accountUserDetail.getAccount().getAccId()).isRegisAccount()) {
                return "redirect:/account/register";
            }
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
        }
        System.out.println("---- Appointment Date Range ----");
        System.out.println(startDate + " - "+endDate);

        String[] start = startDate.split("-"); //dd,mm,yy
        System.out.println("strStart : "+ Arrays.toString(start));
        String[] end = endDate.split("-");
        System.out.println("strEnd : "+ Arrays.toString(end));

        if (startDate.equals(endDate) && !startDate.isEmpty() && !(startDate == null)){
            List<Appointment> appointmentList = appointmentService.findByDate(GlobalService.convertStringToDate(start[2].trim()+"-"+start[1].trim()+"-"+start[0].trim()));
            model.addAttribute("appointments",appointmentList);
            model.addAttribute("dateRange",startDate.replace("-","/") +" - "+ endDate.replace("-","/"));
        } else if (!startDate.isEmpty() && !(startDate == null)) {

            List<Appointment> appointmentList = appointmentService.findByDateBetween(GlobalService.convertStringToDate(start[2].trim()+"-"+start[1].trim()+"-"+start[0].trim()),GlobalService.convertStringToDate(end[2]+"-"+end[1]+"-"+end[0]));
            model.addAttribute("appointments",appointmentList);
            model.addAttribute("dateRange",startDate.replace("-","/") +" - "+ endDate.replace("-","/"));
        }
        List<Appointment> todayList = appointmentService.findByTodayDateOrderByPeriodDesc();
        model.addAttribute("todaySize",todayList.size());

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

    @RequestMapping(path = "/restore", method = POST)
    public String restoreAppointment(@RequestParam("id") long id,
                                     @RequestParam("from") String from){
        Appointment appointment = appointmentService.findByAppointmentID(id);
        if (appointment != null){
            appointment.setStatus(false);
            appointmentService.save(appointment);
            if (from.equals("mainAppointment")){
                return  "redirect:/appointment";
            }
        }
        return  "redirect:/appointment";
    }


}
