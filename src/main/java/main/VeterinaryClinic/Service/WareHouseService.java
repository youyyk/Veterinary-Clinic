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

    public WareHouse create(WareHouse wareHouse) {
        return repository.save(wareHouse);
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



    public void createBillMedAndRemoveStock(Bill bill,Medicine medicine,int removeAmt){
        System.out.println("RemoveAmt (Start) : "+removeAmt);
        List<WareHouse> wareHouses = findByMedicineAndExpiredDateAfterOrderByExpiredDateAsc(medicine,GlobalService.getCurrentTime());
        for (WareHouse wareHouse: wareHouses) {
            if (wareHouse.getQuantityLeft() >= removeAmt) {
                System.out.println("if");
                System.out.println("ItemID : "+wareHouse.getItemID());
                System.out.println("Qty Left(Before) : "+wareHouse.getQuantityLeft());
                wareHouse.setQuantityLeft(wareHouse.getQuantityLeft()-removeAmt);
                System.out.println("Qty Left(After) : "+wareHouse.getQuantityLeft());
                BillMedicine billMedicine = new BillMedicine(bill,wareHouse,removeAmt);
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
                BillMedicine billMedicine = new BillMedicine(bill,wareHouse,wareHouse.getQuantityLeft());
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
            billMedicineService.deleteBillMedicineByBillAndWareHouse_Medicine_MedID(billMed.getBill(),billMed.getWareHouse().getMedicine().getMedID());
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


}
