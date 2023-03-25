package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private MainBillService mainBillService;
    @RequestMapping("/")
    public String getHomePage(Model model, @AuthenticationPrincipal AccountUserDetail accountUserDetail) {
//        model.addAttribute("greeting", "Hi OAuth");
//        if (accountUserDetail != null) {
//            model.addAttribute("user", accountUserDetail.getName());
//            return GlobalService.handleRedirectPageForAccount(accountUserDetail.getAccount(), "home");
//        }
//        else {
//            model.addAttribute("user", "Guest");
//        }
//        return "home";

        List<Appointment> todayAppointment = appointmentService.findByTodayDateOrderByPeriodDesc();

        List<WareHouse> wareHouses = wareHouseService.getAllBySoftDeletedIsFalseOrderByExpiredDateAsc();
        List<WareHouse> needWareHouse = new ArrayList<>();
        int expiredCount = 0;
        int almostCount = 0;
        for (WareHouse wh : wareHouses) {
            short expiredType = wh.isExpired();
            if (expiredType == -1){
                needWareHouse.add(wh);
                expiredCount++;
            } else if (expiredType == 1) {
                needWareHouse.add(wh);
                almostCount++;
            }
        }
//
        List<Bill> bills = mainBillService.findByPaidStatusIsFalseOrderByStartDateAsc();

        model.addAttribute("appointments",todayAppointment );
        model.addAttribute("warehouses",needWareHouse );
        model.addAttribute("bills",bills );
        model.addAttribute("expiredCount",expiredCount );
        model.addAttribute("almostCount",almostCount );



        return "home";
    }
}
