package main.VeterinaryClinic.Service.SubBill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Repository.Bill.BillMedicineRepository;
import main.VeterinaryClinic.Service.MedicineAmt;
import main.VeterinaryClinic.Service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class BillMedicineService {
    @Autowired
    private BillMedicineRepository repository;
    @Autowired
    private MedicineService medicineService;

    public List<BillMedicine> getAll() {
        return repository.findAll();
    }

    public BillMedicine save(BillMedicine billMedicine) {
        return repository.save(billMedicine);
    }

    public List<BillMedicine> findByBill_BillID(long billID) {
        return repository.findByBill_BillID(billID);
    }

    public void deleteBillMedicineByBillAndWareHouse_Medicine_MedID(Bill bill, long medID) {
        repository.deleteBillMedicineByBillAndWareHouse_Medicine_MedID(bill,medID);
    }

    public  List<BillMedicine> findByBillAndWareHouse_Medicine(Bill bill,Medicine medicine){
        return  repository.findByBillAndWareHouse_Medicine(bill, medicine);
    }
    public List<MedicineAmt> countMedicine(Bill bill){
        System.out.println("------ Count Medicines ------");
        List<BillMedicine> billMedicines = repository.findByBill(bill);
        Map<Long,MedicineAmt> count = new TreeMap<>();
        List<MedicineAmt> medList = new ArrayList<>();
        for (BillMedicine billMedicine: billMedicines) {
            if (!(count.containsKey(billMedicine.getWareHouse().getMedicine().getMedID()))){
                System.out.println("Not have this Medicine");
                System.out.println("itemID : "+billMedicine.getWareHouse().getItemID()+ " | Amount :"+billMedicine.getMedTotal());
                count.put(billMedicine.getWareHouse().getMedicine().getMedID(), new MedicineAmt(billMedicine.getWareHouse().getMedicine(), billMedicine.getMedTotal()));
            }
            else if (count.containsKey(billMedicine.getWareHouse().getMedicine().getMedID())){
                System.out.println("Already have this Medicine");
                System.out.println("itemID : "+billMedicine.getWareHouse().getItemID()+ " | Amount :"+billMedicine.getMedTotal());
                MedicineAmt temp = count.get(billMedicine.getWareHouse().getMedicine().getMedID());
                temp.setAmount(billMedicine.getMedTotal()+ temp.getAmount());
                count.put(temp.getMedicine().getMedID(), temp);
            }
//            System.out.println("- - - - -");
//            System.out.println("MedID : "+billMedicine.getWareHouse().getMedicine().getMedID());
//            System.out.println("ItemID : "+billMedicine.getWareHouse().getItemID());
            System.out.println("New Amount : "+ count.get(billMedicine.getWareHouse().getMedicine().getMedID()).getAmount());
//            System.out.println("- - - - -");
        }

        for (Map.Entry<Long,MedicineAmt> med : count.entrySet()) {
            medList.add(med.getValue());
        }
        return medList;

    }




}
