package main.VeterinaryClinic.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String getHomePage(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        model.addAttribute("greeting", "Hi OAuth");
        if (oauth2User != null) {
            System.out.println(oauth2User);
            System.out.println(oauth2User.getAttributes().get("userId"));
            model.addAttribute("user", oauth2User.getName());
        }
        else {
            model.addAttribute("user", "Guest");
        }
        return "home";
    }
}
