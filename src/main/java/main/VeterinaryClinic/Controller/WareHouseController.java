package main.VeterinaryClinic.Controller;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.MedicineService;
import main.VeterinaryClinic.Service.ToolService;
import main.VeterinaryClinic.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/warehouse")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private ToolService toolService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getWarehousePage(Model model, @AuthenticationPrincipal AccountUserDetail accountUserDetail) {
        List<WareHouse> wareHouses = wareHouseService.findBySoftDeletedOrderByExpiredDateAsc(false);
        int expiredCount = 0;
        int almostCount = 0;
        for (WareHouse wh : wareHouses) {
            short expiredType = wh.isExpired();
            if (expiredType == -1){
                expiredCount++;
            } else if (expiredType == 1) {
                almostCount++;
            }
        }
        wareHouses.addAll(wareHouseService.findBySoftDeletedOrderByExpiredDateAsc(true));
        addAttributeModelWarehouse(model, wareHouses, expiredCount, almostCount, accountService.getById(accountUserDetail.getAccount().getAccId()));
        return "warehouse/warehouses";
    }

    @GetMapping("/status/{value}")
    public String getWarehousePageFilterExpired(@PathVariable String value, Model model, @AuthenticationPrincipal AccountUserDetail accountUserDetail) {
        List<WareHouse> wareHouses = wareHouseService.findBySoftDeletedOrderByExpiredDateAsc(false);
        List<WareHouse> needWareHouse = new ArrayList<>();
        int expiredCount = 0;
        int almostCount = 0;
        for (WareHouse wh : wareHouses) {
            short expiredType = wh.isExpired();
            if (expiredType == -1){
                if (value.equals("expired")){ needWareHouse.add(wh);}
                expiredCount++;
            } else if (expiredType == 1) {
                if (value.equals("almost")){needWareHouse.add(wh);}
                almostCount++;
            }
        }
        wareHouses.addAll(wareHouseService.findBySoftDeletedOrderByExpiredDateAsc(true));
        addAttributeModelWarehouse(model, needWareHouse, expiredCount, almostCount, accountService.getById(accountUserDetail.getAccount().getAccId()));
        return "warehouse/warehouses";
    }

    private void addAttributeModelWarehouse(Model model, List<WareHouse> wareHouses, int expiredCount, int almostCount, Account nowAccount){
        model.addAttribute("nowAccount", nowAccount);
        model.addAttribute("warehouses", wareHouses);
        model.addAttribute("expiredCount", expiredCount);
        model.addAttribute("almostCount", almostCount);
        model.addAttribute("medicines", medicineService.findBySoftDeleted(false));
        model.addAttribute("tools", toolService.findBySoftDeleted(false));
        model.addAttribute("newMedForm", new Medicine());
        model.addAttribute("newToolForm", new Tool());
        model.addAttribute("newWarehouseMedForm", new WareHouse());
        model.addAttribute("newWarehouseToolForm", new WareHouse());
    }

    @RequestMapping(path = "/create/medicine", method = RequestMethod.POST)
    public String createWarehouseMedicine(@ModelAttribute Medicine medicine){
        if (medicine != null) {
            medicineService.save(medicine);
        }
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/edit/medicine/{id}", method = RequestMethod.POST)
    public String editWarehouseMedicine(@PathVariable("id") long id,
                                        @RequestParam("name") String name,
                                        @RequestParam("price") double price,
                                        @RequestParam("dose") String dose,
                                        @RequestParam("unit") String unit,
                                        @RequestParam("description") String description){
        Medicine medicine = medicineService.findByMedID(id);
        if (medicine != null) {
            Medicine editMedicine = new Medicine();
            editMedicine.updateFieldForEdit(name, unit, price, description, dose);
            medicineService.updateMedicine(medicine, editMedicine);
        }
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/medicine/delete", method = POST)
    public String deleteMedicine(@RequestParam("id") long id,
                                  @RequestParam("pathId") String pathId){
        Medicine medicine = medicineService.findByMedID(Long.parseLong(pathId));
        if (medicine != null){
            medicineService.delete(medicine);
        }
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/create/tool", method = RequestMethod.POST)
    public String createWarehouseTool(@ModelAttribute Tool tool){
        if (tool != null) {
            toolService.save(tool);
        }
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/edit/tool/{id}", method = RequestMethod.POST)
    public String editWarehouseTool(@PathVariable("id") long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("price") double price,
                                    @RequestParam("description") String description){
        Tool tool = toolService.findByToolID(id);
        if (tool != null) {
            Tool editTool = new Tool();
            editTool.updateFieldForEdit(name, price, description);
            toolService.updateTool(tool, editTool);
        }
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/tool/delete", method = POST)
    public String deleteTool(@RequestParam("id") long id,
                                 @RequestParam("pathId") String pathId){
        Tool tool = toolService.findByToolID(Long.parseLong(pathId));
        if (tool != null){
            toolService.delete(tool);
        }
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/create/order", method = RequestMethod.POST)
    public String createWarehouseOrder(@ModelAttribute WareHouse wareHouse){
        if (wareHouse.getMedicine() != null){
            wareHouseService.save(new WareHouse(wareHouse.getMedicine(), wareHouse.getQuantityIn(), wareHouse.getPaidTotal(), wareHouse.getExpiredDate()));
        } else if (wareHouse.getTool() != null){
            wareHouseService.save(new WareHouse(wareHouse.getTool(), wareHouse.getQuantityIn(), wareHouse.getPaidTotal(), wareHouse.getExpiredDate()));
        }
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/edit/medicine/order/{id}", method = RequestMethod.POST)
    public String editWarehouseMedicineOrder(@PathVariable("id") long id,
                                     @RequestParam("medicine") long medicineId,
                                     @RequestParam("quantityIn") String quantityIn,
                                     @RequestParam("quantityLeft") String quantityLeft,
                                     @RequestParam("paidTotal") String paidTotal,
                                     @RequestParam("stockInDate") String stockInDate,
                                     @RequestParam("expiredDate") String expiredDate){
        updateOrder(id, -1, medicineId, quantityIn, quantityLeft, paidTotal, stockInDate, expiredDate);
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/edit/tool/order/{id}", method = RequestMethod.POST)
    public String editWarehouseToolOrder(@PathVariable("id") long id,
                                     @RequestParam("tool") long toolId,
                                     @RequestParam("quantityIn") String quantityIn,
                                     @RequestParam("quantityLeft") String quantityLeft,
                                     @RequestParam("paidTotal") String paidTotal,
                                     @RequestParam("stockInDate") String stockInDate,
                                     @RequestParam("expiredDate") String expiredDate){
        updateOrder(id, toolId, -1, quantityIn, quantityLeft, paidTotal, stockInDate, expiredDate);
        return "redirect:/warehouse";
    }

    @RequestMapping(path = "/delete", method = POST)
    public String deleteWarehouse(@RequestParam("id") long id,
                                  @RequestParam("pathId") String pathId){
        WareHouse wareHouse = wareHouseService.getById(id);
        if (wareHouse != null){
            wareHouseService.delete(wareHouse);
        }
        return  "redirect:/warehouse";
    }

    @RequestMapping(path = "/stockOut", method = POST)
    public String stockOutWarehouse(@RequestParam("id") long id){
        WareHouse wareHouse = wareHouseService.getById(id);
        if (wareHouse != null){
            wareHouse.setSoftDeleted(true);
            wareHouse.setSoftDeletedDate(GlobalService.getCurrentTime());
            wareHouseService.save(wareHouse);
        }
        return  "redirect:/warehouse";
    }

    @RequestMapping(path = "/restore", method = POST)
    public String restoreWarehouse(@RequestParam("id") long id){
        WareHouse wareHouse = wareHouseService.getById(id);
        if (wareHouse != null){
            wareHouse.setSoftDeleted(false);
            wareHouse.setSoftDeletedDate(null);
            wareHouseService.save(wareHouse);
        }
        return  "redirect:/warehouse";
    }

    private void updateOrder(long warehouseId, long toolId, long medicineId, String quantityIn, String quantityLeft, String paidTotal, String stockInDate, String expiredDate){
        int convertQtyIn = Integer.parseInt(quantityIn);
        int convertQtyLeft = Integer.parseInt(quantityLeft);
        double convertPaidTotal = Double.parseDouble(paidTotal);
        WareHouse wareHouse = wareHouseService.getById(warehouseId);
        if (wareHouse != null) {
            if (wareHouse.getTool() != null) {
                Tool tool = toolService.findByToolID(toolId);
                if (tool != null) {
                    wareHouse.updateFieldForEdit(null, tool, convertQtyIn, convertQtyLeft, convertPaidTotal, GlobalService.convertStringToDate(stockInDate), GlobalService.convertStringToDate(expiredDate));
                }
            }
            else if (wareHouse.getMedicine() != null) {
                Medicine medicine = medicineService.findByMedID(medicineId);
                if (medicine != null) {
                    wareHouse.updateFieldForEdit(medicine, null, convertQtyIn, convertQtyLeft, convertPaidTotal, GlobalService.convertStringToDate(stockInDate), GlobalService.convertStringToDate(expiredDate));
                }
            }
            wareHouseService.save(wareHouse);
        }
    }
}