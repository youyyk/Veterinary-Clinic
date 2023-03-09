package main.VeterinaryClinic.Controller;

import com.nimbusds.jose.shaded.json.JSONObject;
import main.VeterinaryClinic.Config.SecurityConfig;
import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public List<Medicine> getAllMedicine() {
        return medicineService.getAll();
    }


    @GetMapping(path = "/{medID}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sayHello(@PathVariable long medID)
    {
        //Get data from service layer into entityList.
        JSONObject entity = new JSONObject();
        entity.put("description", medicineService.findByMedID(medID).getDescription());

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


}
