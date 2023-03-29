package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.*;
import main.VeterinaryClinic.Service.Account.AccountService;
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
    @Autowired
    private AccountService accountService;

    @RequestMapping("/")
    public String getHomePage(Model model, @AuthenticationPrincipal AccountUserDetail accountUserDetail) {
        if (accountUserDetail!=null){
            return handleRedirectPageForAccountByRole(accountUserDetail);
        }
        return "landing";
    }

    @RequestMapping("/loginSuccess")
    public String getDashboard(@AuthenticationPrincipal AccountUserDetail accountUserDetail) {
        return handleRedirectPageForAccountByRole(accountUserDetail);
    }

    @RequestMapping("/dashboard")
    public String getDashboard(Model model, @AuthenticationPrincipal AccountUserDetail accountUserDetail) {
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
        List<Appointment> todayAppointment = appointmentService.findByTodayDateOrderByPeriodDesc();
        List<Bill> bills = mainBillService.findByPaidStatusIsFalseOrderByStartDateAsc();

        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            model.addAttribute("nowAccount", accountService.getById(accountUserDetail.getAccount().getAccId()));
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

    private String handleRedirectPageForAccountByRole(AccountUserDetail accountUserDetail){
        if (accountUserDetail != null && accountUserDetail.getAccount() != null){
            Account account = accountUserDetail.getAccount();
            if (account.isRegisAccount()) {
                if (account.isAdmin()) {
                    System.out.println("---HANDLE-PAGE : ADMIN---");
                    return "redirect:/dashboard";
                } else if (account.isOfficer()) {
                    System.out.println("---HANDLE-PAGE : OFFICER---");
                    return "redirect:/dashboard";
                } else if (account.isCustomer()) {
                    System.out.println("---HANDLE-PAGE : CUSTOMER---");
                    return "redirect:/account/getInfo/"+account.getAccId();
                }
            }
            return "redirect:/account/register";
        }
        return "redirect:/";
    }
}
