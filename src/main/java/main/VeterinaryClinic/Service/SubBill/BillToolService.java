package main.VeterinaryClinic.Service.SubBill;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Repository.Bill.BillToolRepository;
import main.VeterinaryClinic.Service.MedicineAmt;
import main.VeterinaryClinic.Service.ToolAmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BillToolService {
    @Autowired
    private BillToolRepository repository;

    public List<BillTool> getAll() {
        return repository.findAll();
    }

    public BillTool save(BillTool billTool) {
        return repository.save(billTool);
    }

    public List<BillTool> findByBillAndWareHouse_Tool(Bill bill, Tool tool){
        return  repository.findByBillAndWareHouse_Tool(bill,tool);
    }

    public void deleteBillToolByBillAndWareHouse_Tool_ToolID(Bill bill, long toolID) {
        repository.deleteBillToolByBillAndWareHouse_Tool_ToolID(bill,toolID);
    }

    public List<ToolAmt> countTool(Bill bill){
        System.out.println("------ Count Tool ------");
        List<BillTool> billTools = repository.findByBill(bill);
        Map<Long, ToolAmt> count = new TreeMap<>();
        List<ToolAmt> toolList = new ArrayList<>();
        for (BillTool billTool: billTools) {
            if (!(count.containsKey(billTool.getWareHouse().getTool().getToolID()))){
                System.out.println("Not have this Tool");
                System.out.println("itemID : "+billTool.getWareHouse().getItemID()+ " | Amount :"+billTool.getToolTotal());

                count.put(billTool.getWareHouse().getTool().getToolID(), new ToolAmt(billTool.getWareHouse().getTool(), billTool.getToolTotal()));
            }
            else if (count.containsKey(billTool.getWareHouse().getTool().getToolID())){
                System.out.println("Already have this Tool");
                System.out.println("itemID : "+billTool.getWareHouse().getItemID()+ " | Amount :"+billTool.getToolTotal());

                ToolAmt temp = count.get(billTool.getWareHouse().getTool().getToolID());
                temp.setAmount(billTool.getToolTotal()+ temp.getAmount());
                count.put(temp.getTool().getToolID(), temp);
            }
//            System.out.println("- - - - -");
//            System.out.println("ToolID : "+billTool.getWareHouse().getTool().getToolID());
//            System.out.println("ItemID : "+billTool.getWareHouse().getItemID());
            System.out.println("New Amount : "+ count.get(billTool.getWareHouse().getTool().getToolID()).getAmount());
//            System.out.println("- - - - -");
        }

        for (Map.Entry<Long,ToolAmt> tool : count.entrySet()) {
            toolList.add(tool.getValue());
        }
        return toolList;
    }


}
