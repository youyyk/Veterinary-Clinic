package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Service.GlobalService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String getHomePage(Model model, @AuthenticationPrincipal AccountUserDetail accountUserDetail) {
        model.addAttribute("greeting", "Hi OAuth");
        if (accountUserDetail != null) {
            model.addAttribute("user", accountUserDetail.getName());
            return GlobalService.handleRedirectPageForAccount(accountUserDetail.getAccount(), "home");
        }
        else {
            model.addAttribute("user", "Guest");
        }
        return "home";
    }
}
