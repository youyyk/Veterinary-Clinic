package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillServing;
import org.springframework.stereotype.Service;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillServiceService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Service
public class GlobalService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PetService petService;
    @Autowired
    private MainBillService mainBillService;
    @Autowired
    private BillMedicineService billMedicineService;
    @Autowired
    private BillToolService billToolService;
    @Autowired
    private BillServiceService billServiceService;
    @Autowired
    private ToolService toolService;
    @Autowired
    private ServingService servingService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private TreatmentHistoryService treatmentHistoryService;
    @Autowired
    private GenerateFileService generateFileService;

    public static String getStringCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public static Date getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return date;
    }

    public static String convertStringToDateSlash(Date date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = formatter.format(date);
            System.out.println("Convert : "+ strDate);
            return strDate;
        }
        catch(Exception e){
            System.out.println("Global : Cannot Convert String to Date");
        }
        return null;
    }
    public static Date convertStringToDate(String strDate) {
//        System.out.println("Before : "+strDate);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(strDate);
            return date;
        }
        catch(Exception e){
            System.out.println("Global : Cannot Convert String to Date");
        }
        return null;
    }

    public static String handleRedirectPageForAccount(Account account, String goToPath){
        if (account.isRegisAccount()) {
            return goToPath;
        }
        return "redirect:/account/register";
    }

    public void mockData() {
        // You
        Account account = new Account("youyyk", "Ua740d4c84ff34de6a233ae21b0db03ab", "");
        accountService.save(account);
        accountService.addRoleAdmin(account);
        Pet pet = new Pet(account,"Jummeng","male", GlobalService.convertStringToDate("2021-10-05"),
                true,"Dog","Golden","");
        // Peang
        account = new Account("Miss","Nichanan","Chatuparsutisin",
                "112 No 70 Sukhumvit 24, Khlong Tan, Khlong Toey, Bangkok,Thailand","0853697548");
        accountService.save(account);
        accountService.addRoleAdmin(account);
//        Pet pet = new Pet(account,"Jummeng","male", GlobalService.convertStringToDate("2021-10-05"),
//                true,"Dog","Golden","");
        petService.save(pet);
        account = new Account("peang", "Udfc07b467d731d48428a2bcb0167abc6", "");
        accountService.save(account);
        accountService.addRoleAdmin(account);
//        for (int i=1; i<=10; i++){
//            accountService.create("Test"+i, "user"+i,null);
//        }
        //------------- Pet --------------
        pet = new Pet(accountService.getByLineId("Ua740d4c84ff34de6a233ae21b0db03ab"),"Mini","female", GlobalService.convertStringToDate("2022-05-27"),
                true,"Dog","Pom","");
        petService.save(pet);

        pet = new Pet(accountService.getByLineId("Ua740d4c84ff34de6a233ae21b0db03ab"),"Lupin","male", GlobalService.convertStringToDate("2022-03-12"),
                true,"Cat","Persia","");
        petService.save(pet);


        //        ------------- Medicine ------------

        Medicine medicine = new Medicine("Previcox","tablet",75,"ครั้งละ 1 เม็ด หลังอาหารเย็น","57 mg");
        medicineService.save(medicine);
        wareHouseService.save(new WareHouse(medicine, 200,13000,GlobalService.convertStringToDate("2023-03-15")));
        wareHouseService.save(new WareHouse(medicine, 150,8000,GlobalService.convertStringToDate("2023-05-20")));
        wareHouseService.save(new WareHouse(medicine, 200,13000,GlobalService.convertStringToDate("2023-10-06")));

        medicine = new Medicine("Tramadol HCL","capsule",15,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","50 mg");
        medicineService.save(medicine);
        wareHouseService.save(new WareHouse(medicine, 100,1440,GlobalService.convertStringToDate("2023-11-27")));
        wareHouseService.save(new WareHouse(medicine, 100,1440,GlobalService.convertStringToDate("2024-01-06")));
        wareHouseService.save(new WareHouse(medicine, 120,1440,GlobalService.convertStringToDate("2024-12-08")));

        medicine = new Medicine("Toflex/Cephalexin","capsule",20,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","250mg");
        medicineService.save(medicine);
        wareHouseService.save(new WareHouse(medicine, 50,500,GlobalService.convertStringToDate("2022-02-12")));
        wareHouseService.save(new WareHouse(medicine, 50,500,GlobalService.convertStringToDate("2023-04-17")));
        wareHouseService.save(new WareHouse(medicine, 100,1000,GlobalService.convertStringToDate("2023-06-11")));
        wareHouseService.save(new WareHouse(medicine, 200,2000,GlobalService.convertStringToDate("2023-09-02")));

        medicine = new Medicine("Defensor","dose",85,"vaccine");
        medicineService.save(medicine);
        wareHouseService.save(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2022-01-22")));
        wareHouseService.save(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2023-05-27")));
        wareHouseService.save(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2023-11-07")));

        medicine = new Medicine("Hepato support","capsule",35,"ครั้งละ 1 เม็ด หลังอาหารเย็น");
        medicineService.save(medicine);
        wareHouseService.save(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2022-01-22")));
        wareHouseService.save(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2023-05-27")));

        //        //------------- Tool ------------
        Tool toolData = new Tool("Syringe",10,"5 ml");
        toolService.save(toolData);
        wareHouseService.save(new WareHouse(toolData, 100,800,GlobalService.convertStringToDate("2022-01-12")));
        wareHouseService.save(new WareHouse(toolData, 300,24000,GlobalService.convertStringToDate("2023-04-02")));
        wareHouseService.save(new WareHouse(toolData, 300,24000,GlobalService.convertStringToDate("2023-07-12")));
        toolData = new Tool("Syringe",5,"3 ml");
        toolService.save(toolData);
        wareHouseService.save(new WareHouse(toolData, 150,6000,GlobalService.convertStringToDate("2023-03-12")));
        wareHouseService.save(new WareHouse(toolData, 150,6000,GlobalService.convertStringToDate("2023-07-01")));
        toolData = new Tool("Syringe",10,"1 ml");
        toolService.save(toolData);
        wareHouseService.save(new WareHouse(toolData, 100,900,GlobalService.convertStringToDate("2023-10-13")));
        toolData = new Tool("Needle",5,"23");
        toolService.save(toolData);
        wareHouseService.save(new WareHouse(toolData, 350,1050,GlobalService.convertStringToDate("2023-05-21")));
        toolData = new Tool("Glove",50,"7.5");
        toolService.save(toolData);
        wareHouseService.save(new WareHouse(toolData, 500,24000,GlobalService.convertStringToDate("2024-02-26")));
        toolData = new Tool("ชุดผ่าตัด",300,"7.5");
        toolService.save(toolData);
        wareHouseService.save(new WareHouse(toolData, 250,6250,GlobalService.convertStringToDate("2024-12-06")));

        ////        //------------- Service ------------
        Serving serviceData = new Serving("เปิดห้องผ่าตัด",300);
        servingService.save(serviceData);
        serviceData = new Serving("Doctor Fee",170);
        servingService.save(serviceData);
        serviceData = new Serving("Isoflurane",750);
        servingService.save(serviceData);

        //        ------------- Bill And Treatment History--------------
        TreatmentHistory treatmentHistory = new TreatmentHistory(petService.findByPetID(1), GlobalService.convertStringToDate("2023-03-03"),
                                                        "ฉีดยาพิษสุนัขบ้า",20);
        Bill bill = new Bill();
        bill.setStartDate(GlobalService.getCurrentTime());
        treatmentHistory.setBill(bill);
        bill.setTreatmentHistory(treatmentHistory);
        treatmentHistoryService.save(treatmentHistory);
        bill = mainBillService.findByBillID(1);
//        System.out.println("--Bill--"+bill);
//        System.out.println("--treatment--"+treatmentHistoryService.findByTreatmentHisID(1));

        medicine = medicineService.findByMedID(4);
        wareHouseService.createBillMedAndRemoveStock(bill,medicine,1,medicine.getDescription());
        bill.setTotal(bill.getTotal()+(medicine.getPrice()*1));
        Tool tool = toolService.findByToolID(1);
        wareHouseService.createBillToolAndRemoveStock(bill,tool,1);
        bill.setTotal(bill.getTotal()+(tool.getPrice()*1));
        Serving serving = servingService.findByServiceID(2);
        BillServing billServing = new BillServing(bill,serving,1);
        billServiceService.save(billServing);
        bill.setTotal(bill.getTotal()+(serving.getPrice()*1));
        mainBillService.save(bill);

//        - - - - - - - - - - - - - - - - - - - - -

        treatmentHistory = new TreatmentHistory(petService.findByPetID(1), GlobalService.convertStringToDate("2023-03-14"),
                                        "ถ่ายพยาธิ",21.7);
        bill = new Bill();
        bill.setStartDate(GlobalService.getCurrentTime());
        treatmentHistory.setBill(bill);
        bill.setTreatmentHistory(treatmentHistory);
        treatmentHistoryService.save(treatmentHistory);
        bill = mainBillService.findByBillID(2);
//        System.out.println("--Bill--"+bill);
//        System.out.println("--treatment--"+treatmentHistoryService.findByTreatmentHisID(2));
        medicine = medicineService.findByMedID(1);
        wareHouseService.createBillMedAndRemoveStock(bill,medicine,2,medicine.getDescription());
        bill.setTotal(bill.getTotal()+(medicine.getPrice()*2));
        medicine = medicineService.findByMedID(3);
        wareHouseService.createBillMedAndRemoveStock(bill,medicine,1,medicine.getDescription());
        bill.setTotal(bill.getTotal()+(medicine.getPrice()*1));
        tool = toolService.findByToolID(1);
        wareHouseService.createBillToolAndRemoveStock(bill,tool,1);
        bill.setTotal(bill.getTotal()+(tool.getPrice()*1));
        billServing = new BillServing(bill,serving,1);
        billServiceService.save(billServing);
        bill.setTotal(bill.getTotal()+(serving.getPrice()*1));
        mainBillService.save(bill);

    //        ------------- Appointment --------------

        pet = petService.findByPetID(1);
        Appointment appointment = new Appointment(pet,GlobalService.convertStringToDate("2023-04-01"),"morning","ดูแผล");
        appointmentService.save(appointment);
        pet = petService.findByPetID(1);
        appointment = new Appointment(pet,GlobalService.convertStringToDate("2023-04-17"),"afternoon","vaccine");
        appointmentService.save(appointment);
        pet = petService.findByPetID(3);
        appointment = new Appointment(pet,GlobalService.convertStringToDate("2023-04-13"),"morning","ดูแผล");
        appointmentService.save(appointment);
        pet = petService.findByPetID(2);
        appointment = new Appointment(pet,GlobalService.convertStringToDate("2023-04-13"),"afternoon","ถ่ายพยาธิ");
        appointmentService.save(appointment);




    }
}