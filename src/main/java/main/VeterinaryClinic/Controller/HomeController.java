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
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private ToolService toolService;
    @RequestMapping("/")
    public String getHomePage(Model model, @AuthenticationPrincipal AccountUserDetail accountUserDetail) {
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

        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            model.addAttribute("nowAccount", accountUserDetail.getAccount());
        }

        if (accountUserDetail == null){
            return "redirect:/landing";
        }

        model.addAttribute("appointments",todayAppointment );
        model.addAttribute("warehouses",needWareHouse );
        model.addAttribute("bills",bills );
        model.addAttribute("expiredCount",expiredCount );
        model.addAttribute("almostCount",almostCount );
        model.addAttribute("medicines", medicineService.findBySoftDeleted(false));
        model.addAttribute("tools", toolService.findBySoftDeleted(false));


        return "home";
    }

    @RequestMapping("/landing")
    public String accountUser(@AuthenticationPrincipal AccountUserDetail accountUserDetail) {

        return "landing";
    }

}
