package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.Serving;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.ServingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServingService servingService;

    @GetMapping
    public String getServicePage(@AuthenticationPrincipal AccountUserDetail accountUserDetail, Model model) {
        List<Serving> servings = servingService.getAll();
        model.addAttribute("services", servings);
        model.addAttribute("newService", new Serving());
        if (accountUserDetail != null && accountUserDetail.getAccount() != null) {
            model.addAttribute("nowAccount", accountUserDetail.getAccount());
        }
        return "service/services";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createWarehouseMedicine(@ModelAttribute Serving serving){
        if (serving != null) {
            servingService.save(serving);
        }
        return "redirect:/service";
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
    public String createWarehouseMedicine(@PathVariable("id") long id,
                                          @RequestParam("name") String name,
                                          @RequestParam("price") double price){
        Serving serving = servingService.findByServiceID(id);
        if (serving != null){
            serving.updateFieldForEdit(name,price);
            servingService.save(serving);
        }
        return "redirect:/service";
    }

    @RequestMapping(path = "/delete", method = POST)
    public String deleteService(@RequestParam("id") long id,
                            @RequestParam("pathId") String pathId){
        System.out.println("---- Delete Service ----");

        Serving serving = servingService.findByServiceID(id);

        serving.setSoftDeleted(true);
        serving.setSoftDeletedDate(GlobalService.getCurrentTime());

        servingService.save(serving);

        System.out.println("Delete "+serving.getName());

        return "redirect:/service";
    }
}
