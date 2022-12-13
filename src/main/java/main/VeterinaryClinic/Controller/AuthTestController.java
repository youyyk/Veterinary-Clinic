package main.VeterinaryClinic.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authTest")
public class AuthTestController {
    @GetMapping
    public String getAuthTest() {
        return "authTest";
    }
}
