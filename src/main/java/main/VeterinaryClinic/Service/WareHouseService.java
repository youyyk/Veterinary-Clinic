package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Repository.MedicineRepository;
import main.VeterinaryClinic.Repository.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseService {
    @Autowired
    private WareHouseRepository repository;

    public List<WareHouse> getAll() {
        return repository.findAll();
    }

    public WareHouse create(WareHouse wareHouse) {
        return repository.save(wareHouse);
    }

    public void removeStock(Medicine medicine,int removeAmt) {
        List<WareHouse> wareHouses = repository.findByMedicineAndSoftDeletedAndExpiredDateBeforeOrderByCreatedDateAsc(medicine,false,GlobalService.getCurrentTime());
        System.out.println("Medicine's name : "+medicine.getName());
        removeStockWarehouse(wareHouses,removeAmt);

    }

    public void removeStock(Tool tool, int removeAmt) {
        List<WareHouse> wareHouses = repository.findByToolAndSoftDeletedAndExpiredDateBeforeOrderByCreatedDateAsc(tool,false,GlobalService.getCurrentTime());
        System.out.println("Tool's name : "+tool.getName());
        removeStockWarehouse(wareHouses,removeAmt);
    }

    private WareHouse removeStockWarehouse(List<WareHouse> wareHouses,int removeAmt){
        for (int i = 0; i < wareHouses.size(); i++) {
            WareHouse wareHouse = wareHouses.get(i);
            if (wareHouse.getQuantityLeft() >= removeAmt){
                System.out.println("Qty Left(Before) : "+wareHouse.getQuantityLeft());
                wareHouse.setQuantityLeft(wareHouse.getQuantityLeft()-removeAmt);
                System.out.println("Qty Left(After) : "+wareHouse.getQuantityLeft());
                return repository.save(wareHouse);
            }
            else if (wareHouse.getQuantityLeft() > 0 && wareHouse.getQuantityLeft() < removeAmt){
                System.out.println("Qty Left(Before) : "+wareHouse.getQuantityLeft());
                removeAmt-= wareHouse.getQuantityLeft();
                wareHouse.setQuantityLeft(0);
                System.out.println("Qty Left(After) : "+wareHouse.getQuantityLeft());
                repository.save(wareHouses.get(i));
            }
        }
        return null;
    }


}
