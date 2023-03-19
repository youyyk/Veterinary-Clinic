package main.VeterinaryClinic.Config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // get the error status code
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        // check if the status code is not null
        if (statusCode != null) {
            // handle different error codes
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            }
            else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
        }

        // if the status code is not handled, return the default error page
        return "error/default";
    }
}

