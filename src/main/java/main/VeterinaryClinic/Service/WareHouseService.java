package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Repository.WareHouseRepository;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class WareHouseService {
    @Autowired
    private WareHouseRepository repository;
    @Autowired
    private BillMedicineService billMedicineService;
    @Autowired
    private BillToolService billToolService;

    @Autowired
    private MainBillService mainBillService;

    public List<WareHouse> getAll() {
        return repository.findAll();
    }

    public WareHouse getById(long id) {
        return repository.findByItemID(id);
    }

    public WareHouse findByItemID(long itemID) {
        return repository.findByItemID(itemID);
    }

    public List<WareHouse> findByMedicineAndExpiredDateBeforeOrderByExpiredDateAsc(Medicine medicine, Date expiredDate){
        return repository.findByMedicineAndSoftDeletedAndExpiredDateBeforeOrderByExpiredDateAsc(medicine,false,expiredDate);
    }

    public List<WareHouse> findByMedicineAndExpiredDateAfterOrderByExpiredDateAsc(Medicine medicine, Date expiredDate){
        return repository.findByMedicineAndSoftDeletedAndExpiredDateAfterOrderByExpiredDateAsc(medicine,false,expiredDate);
    }


    public List<WareHouse> findByToolAndExpiredDateBeforeOrderByExpiredDateAsc(Tool tool, Date expiredDate){
        return repository.findByToolAndSoftDeletedAndExpiredDateBeforeOrderByExpiredDateAsc(tool,false,expiredDate);
    }

    public List<WareHouse> findByToolAndSoftDeletedAndExpiredDateAfterOrderByExpiredDateAsc(Tool tool, Date expiredDate){
        return repository.findByToolAndSoftDeletedAndExpiredDateAfterOrderByExpiredDateAsc(tool,false,expiredDate);
    }


    public void createBillMedAndRemoveStock(Bill bill,Medicine medicine,int removeAmt,String description){
        System.out.println("RemoveAmt (Start) : "+removeAmt);
        List<WareHouse> wareHouses = findByMedicineAndExpiredDateAfterOrderByExpiredDateAsc(medicine,GlobalService.getCurrentTime());
        for (WareHouse wareHouse: wareHouses) {
            if (wareHouse.getQuantityLeft() >= removeAmt) {
                System.out.println("if");
                System.out.println("ItemID : "+wareHouse.getItemID());
                System.out.println("Qty Left(Before) : "+wareHouse.getQuantityLeft());
                wareHouse.setQuantityLeft(wareHouse.getQuantityLeft()-removeAmt);
                System.out.println("Qty Left(After) : "+wareHouse.getQuantityLeft());
                BillMedicine billMedicine = new BillMedicine(bill,wareHouse, description, removeAmt);
                billMedicineService.save(billMedicine);
                repository.save(wareHouse);
                removeAmt = 0;
                System.out.println("RemoveAmt (End): "+removeAmt);
                break;
            }
            else if (wareHouse.getQuantityLeft() > 0 && wareHouse.getQuantityLeft() < removeAmt){
                System.out.println("else if");
                System.out.println("ItemID : "+wareHouse.getItemID());
                System.out.println("Qty Left(Before) : "+wareHouse.getQuantityLeft());
                removeAmt -= wareHouse.getQuantityLeft();
                BillMedicine billMedicine = new BillMedicine(bill,wareHouse,description,wareHouse.getQuantityLeft());
                wareHouse.setQuantityLeft(0);
                System.out.println("Qty Left(After) : "+wareHouse.getQuantityLeft());
                billMedicineService.save(billMedicine);
                repository.save(wareHouse);
                System.out.println("RemoveAmt : "+removeAmt);
                System.out.println("- - - -");
            }
        }
    }

    public void createBillToolAndRemoveStock(Bill bill,Tool tool,int removeAmt){
        List<WareHouse> wareHouses = findByToolAndSoftDeletedAndExpiredDateAfterOrderByExpiredDateAsc(tool,GlobalService.getCurrentTime());
        for (WareHouse wareHouse: wareHouses) {
            if (wareHouse.getQuantityLeft() >= removeAmt) {
                System.out.println("if");
                System.out.println("ItemID : "+wareHouse.getItemID());
                System.out.println("ItemID : "+wareHouse.getItemID());
                System.out.println("Qty Left(Before) : "+wareHouse.getQuantityLeft());
                wareHouse.setQuantityLeft(wareHouse.getQuantityLeft()-removeAmt);
                System.out.println("Qty Left(After) : "+wareHouse.getQuantityLeft());
                BillTool billTool = new BillTool(bill,wareHouse,removeAmt);
                billToolService.save(billTool);
                repository.save(wareHouse);
                removeAmt = 0;
                System.out.println("RemoveAmt (End): "+removeAmt);
                break;
            }
            else if (wareHouse.getQuantityLeft() > 0 && wareHouse.getQuantityLeft() < removeAmt){
                System.out.println("else if");
                System.out.println("ItemID : "+wareHouse.getItemID());
                System.out.println("Qty Left(Before) : "+wareHouse.getQuantityLeft());
                removeAmt-= wareHouse.getQuantityLeft();
                BillTool billTool = new BillTool(bill,wareHouse,wareHouse.getQuantityLeft());
                wareHouse.setQuantityLeft(0);
                System.out.println("Qty Left(After) : "+wareHouse.getQuantityLeft());
                billToolService.save(billTool);
                repository.save(wareHouse);
                System.out.println("RemoveAmt : "+removeAmt);
                System.out.println("- - - -");
            }
        }
    }

    public void deleteBillMedAndRecoverStock(Bill bill,Medicine medicine) {
        System.out.println("Recover "+medicine.getName());
        List<BillMedicine> billMedicineList = billMedicineService.findByBillAndWareHouse_Medicine(bill,medicine);
        double newBillTotal = bill.getTotal();

        for (BillMedicine billMed: billMedicineList) {
            newBillTotal -= billMed.getMedTotal() * medicine.getPrice();

            WareHouse recoverStock = repository.findByItemID(billMed.getWareHouse().getItemID());
            recoverStock.setQuantityLeft(recoverStock.getQuantityLeft()+billMed.getMedTotal());
            billMedicineService.deleteBillMedicineByBill_AndWareHouse_ItemID(bill,billMed.getWareHouse().getItemID());
//            billMedicineService.deleteBillMedicineByBillAndWareHouse_Medicine_MedID(billMed.getBill(),billMed.getWareHouse().getMedicine().getMedID());
        }

        bill.setTotal(newBillTotal);
        mainBillService.save(bill);

    }

    public void deleteBillToolAndRecoverStock(Bill bill,Tool tool) {
        System.out.println("Recover "+tool.getName());
        List<BillTool> billToolList = billToolService.findByBillAndWareHouse_Tool(bill,tool);
        double newBillTotal = bill.getTotal();

        for (BillTool billTool: billToolList) {
            newBillTotal -= billTool.getToolTotal() * tool.getPrice();

            WareHouse recoverStock = repository.findByItemID(billTool.getWareHouse().getItemID());
            recoverStock.setQuantityLeft(recoverStock.getQuantityLeft()+billTool.getToolTotal());
            billToolService.deleteBillToolByBillAndWareHouse_Tool_ToolID(billTool.getBill(),billTool.getWareHouse().getTool().getToolID());
        }

        bill.setTotal(newBillTotal);
        mainBillService.save(bill);

    }

    public void editBillMedicine(Bill bill,Medicine medicine,int oldAmount,int newAmount,String description){
        int diffAmt = newAmount-oldAmount;
        System.out.println("Old Amt : "+oldAmount+" | New Amt : "+newAmount+" | Diff Amt : "+diffAmt);
        System.out.println("Description : "+description);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (diffAmt < 0){
            List<BillMedicine> billMedicineList = billMedicineService.findByBillAndWareHouse_Medicine(bill,medicine);
            billMedicineList.sort(Comparator.comparing((BillMedicine o) -> sdf.format(o.getWareHouse().getExpiredDate())).reversed());
            diffAmt = Math.abs(diffAmt);
            for (BillMedicine billMed: billMedicineList) {
                System.out.println(billMed.getPairedID().toString());
                System.out.println("old med total : "+billMed.getMedTotal());
                if (billMed.getMedTotal() > diffAmt){
                    // recover stock
                    billMed.getWareHouse().setQuantityLeft(billMed.getWareHouse().getQuantityLeft()+diffAmt);
                    // delete from medTotal
                    billMed.setMedTotal(billMed.getMedTotal()-diffAmt);
                    billMedicineService.save(billMed);
                    System.out.println("new med total : "+billMed.getMedTotal());
                    diffAmt = 0;
                    System.out.println("Diff Amt (>): "+diffAmt);
                    break;
                }
                else if (billMed.getMedTotal() == diffAmt) {
                    // recover stock
                    billMed.getWareHouse().setQuantityLeft(billMed.getWareHouse().getQuantityLeft()+diffAmt);
                    // delete bill medicine
                    billMedicineService.deleteBillMedicineByBill_AndWareHouse_ItemID(billMed.getBill(),billMed.getWareHouse().getItemID());
//                    billMedicineService.save(billMed);
                    System.out.println("new med total : 0");
                    diffAmt = 0;
                    System.out.println("Diff Amt (=): "+diffAmt);
                    break;
                }
                else if (billMed.getMedTotal() < diffAmt) {
                    // recover stock
                    billMed.getWareHouse().setQuantityLeft(billMed.getWareHouse().getQuantityLeft()+billMed.getMedTotal());
                    diffAmt -= billMed.getMedTotal();
                    billMed.setMedTotal(0);
                    billMedicineService.save(billMed);
                    billMedicineService.deleteBillMedicineByBill_AndWareHouse_ItemID(billMed.getBill(),billMed.getWareHouse().getItemID());
                    System.out.println("new med total : "+billMed.getMedTotal());
                    System.out.println("Diff Amt (<): "+diffAmt);
                    System.out.println("- - - - ");
                }
            }
        }
        else if (diffAmt > 0) {
            List<BillMedicine> billMedicineList = billMedicineService.findByBillAndWareHouse_Medicine(bill,medicine);
            billMedicineList.sort(Comparator.comparing((BillMedicine o) -> sdf.format(o.getWareHouse().getExpiredDate())));
            for (BillMedicine billMed: billMedicineList) {
                if (billMed.getWareHouse().getQuantityLeft() >= diffAmt){
                    billMed.getWareHouse().setQuantityLeft(billMed.getWareHouse().getQuantityLeft()-diffAmt);
                    billMed.setMedTotal(billMed.getMedTotal()+diffAmt);
                    billMedicineService.save(billMed);
                    diffAmt -= billMed.getMedTotal();
                    System.out.println("new med total : "+billMed.getMedTotal());
                    System.out.println("Diff Amt (>= diff): "+diffAmt);
                    billMedicineService.save(billMed);
                    break;
                }
                else if (billMed.getWareHouse().getQuantityLeft() < diffAmt) {
                    billMed.setMedTotal(billMed.getMedTotal()+billMed.getWareHouse().getQuantityLeft());
                    diffAmt -= billMed.getWareHouse().getQuantityLeft();
                    billMed.getWareHouse().setQuantityLeft(0);
                    System.out.println("new med total : "+billMed.getMedTotal());
                    System.out.println("Diff Amt (< diff): "+diffAmt);
                    System.out.println("- - - - ");
                    billMedicineService.save(billMed);
                }
            }
            if (diffAmt > 0){
                createBillMedAndRemoveStock(bill,medicine,diffAmt,description);
            }
        }
        List<BillMedicine> setBillMedDescription = billMedicineService.findByBillAndWareHouse_Medicine(bill,medicine);
        for (BillMedicine billMed: setBillMedDescription) {
            billMed.setNewDescription(description);
            billMedicineService.save(billMed);
        }

    }

    public void editBillTool(Bill bill,Tool tool,int oldAmount,int newAmount){
        int diffAmt = newAmount-oldAmount;
        System.out.println("Old Amt : "+oldAmount+" | New Amt : "+newAmount+" | Diff Amt : "+diffAmt);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (diffAmt < 0){
            List<BillTool> billToolList = billToolService.findByBillAndWareHouse_Tool(bill,tool);
            billToolList.sort(Comparator.comparing((BillTool o) -> sdf.format(o.getWareHouse().getExpiredDate())).reversed());
            diffAmt = Math.abs(diffAmt);

            for (BillTool billTool: billToolList) {
                System.out.println(billTool.getPairedID().toString());
                System.out.println("old tool total : "+billTool.getToolTotal());
                if (billTool.getToolTotal() > diffAmt){
                    // recover stock
                    billTool.getWareHouse().setQuantityLeft(billTool.getWareHouse().getQuantityLeft()+diffAmt);
                    // delete from tool Total
                    billTool.setToolTotal(billTool.getToolTotal()-diffAmt);
                    billToolService.save(billTool);
                    System.out.println("new tool total : "+billTool.getToolTotal());
                    diffAmt = 0;
                    System.out.println("Diff Amt (>): "+diffAmt);
                    break;
                }
                else if (billTool.getToolTotal() == diffAmt) {
                    // recover stock
                    billTool.getWareHouse().setQuantityLeft(billTool.getWareHouse().getQuantityLeft()+diffAmt);
                    // delete bill tool
                    billToolService.deleteBillToolByBillAndWareHouse_ItemID(billTool.getBill(),billTool.getWareHouse().getItemID());
                    System.out.println("new tool total : 0");
                    diffAmt = 0;
                    System.out.println("Diff Amt (=): "+diffAmt);
                    break;
                }
                else if (billTool.getToolTotal() < diffAmt) {
                    // recover stock
                    billTool.getWareHouse().setQuantityLeft(billTool.getWareHouse().getQuantityLeft()+billTool.getToolTotal());
                    diffAmt -= billTool.getToolTotal();
                    billTool.setToolTotal(0);
                    billToolService.save(billTool);
                    billToolService.deleteBillToolByBillAndWareHouse_ItemID(billTool.getBill(),billTool.getWareHouse().getItemID());
                    System.out.println("new tool total : "+billTool.getToolTotal());
                    System.out.println("Diff Amt (<): "+diffAmt);
                    System.out.println("- - - - ");
                }
            }
        }
        else if (diffAmt > 0) {
            List<BillTool> billToolList = billToolService.findByBillAndWareHouse_Tool(bill,tool);
            billToolList.sort(Comparator.comparing((BillTool o) -> sdf.format(o.getWareHouse().getExpiredDate())));
            for (BillTool billTool: billToolList) {
                if (billTool.getWareHouse().getQuantityLeft() >= diffAmt){
                    billTool.getWareHouse().setQuantityLeft(billTool.getWareHouse().getQuantityLeft()-diffAmt);
                    billTool.setToolTotal(billTool.getToolTotal()+diffAmt);
                    billToolService.save(billTool);
                    diffAmt -= billTool.getToolTotal();
                    System.out.println("new tool total : "+billTool.getToolTotal());
                    System.out.println("Diff Amt (>= diff): "+diffAmt);
                    billToolService.save(billTool);
                    break;
                }
                else if (billTool.getWareHouse().getQuantityLeft() < diffAmt) {
                    billTool.setToolTotal(billTool.getToolTotal()+billTool.getWareHouse().getQuantityLeft());
                    diffAmt -= billTool.getWareHouse().getQuantityLeft();
                    billTool.getWareHouse().setQuantityLeft(0);
                    System.out.println("new tool total : "+billTool.getToolTotal());
                    System.out.println("Diff Amt (< diff): "+diffAmt);
                    System.out.println("- - - - ");
                    billToolService.save(billTool);
                }
            }
            if (diffAmt > 0){
                createBillToolAndRemoveStock(bill,tool,diffAmt);
            }
        }
    }


    public WareHouse save(WareHouse wareHouse) {
        return repository.save(wareHouse);
    }
}
