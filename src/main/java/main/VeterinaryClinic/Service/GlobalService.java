package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Bill.Bill;
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
        System.out.println("Before : "+strDate);
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


    public static String handleRedirectPageForAccountByRole(Account account){
        if (account.isRegisAccount()) {
            if (account.isAdmin()) {
                System.out.println("---HANDLE-PAGE : ADMIN---");
                return "redirect:/account";
            } else if (account.isOfficer()) {
                System.out.println("---HANDLE-PAGE : OFFICER---");
                return "redirect:/warehouse";
            } else if (account.isCustomer()) {
                System.out.println("---HANDLE-PAGE : CUSTOMER---");
                return "redirect:/account/getInfo/"+account.getAccId();
            }
        }
        return "redirect:/account/register";
    }

    public static String handleRedirectPageForAccount(Account account, String goToPath){
        if (account.isRegisAccount()) {
            return goToPath;
        }
        return "redirect:/account/register";
    }

    public void mockData() {
        mainBillService.deleteBillByBillID(1);
        Account uTest = new Account("me", "Ua740d4c84ff34de6a233ae21b0db03ab", "");
        accountService.save(uTest);
        accountService.addRoleAdmin(uTest);
        Account pTest = new Account("peang", "Udfc07b467d731d48428a2bcb0167abc6", "");
        accountService.save(pTest);
        accountService.addRoleAdmin(pTest);
        for (int i=1; i<=5; i++){
            accountService.create("Test"+i, "user"+i,null);
        }
        //------------- Pet --------------
        Pet pet = new Pet(accountService.getByLineId("Ua740d4c84ff34de6a233ae21b0db03ab"),"Mini","female", GlobalService.convertStringToDate("2022-05-27"),
                true,"Dog","Pom","");
        petService.save(pet);

        pet = new Pet(accountService.getByLineId("Ua740d4c84ff34de6a233ae21b0db03ab"),"Lupin","male", GlobalService.convertStringToDate("2022-03-12"),
                true,"Cat","Persia","");
        petService.save(pet);

        //        ------------- Medicine ------------

        Medicine medicine = new Medicine("Previcox","tablet",75,"ครั้งละ 1 เม็ด หลังอาหารเย็น","57 mg");
        medicineService.save(medicine);
        wareHouseService.save(new WareHouse(medicine, 100,6500,GlobalService.convertStringToDate("2022-01-22")));
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

        ////        //------------- Service ------------
        Serving serviceData = new Serving("เปิดห้องผ่าตัด",300);
        servingService.save(serviceData);
        serviceData = new Serving("Doctor Fee",170);
        servingService.save(serviceData);

        //        ------------- Bill And Treatment History--------------
        TreatmentHistory treatmentHistory = new TreatmentHistory(petService.findByPetID(1), GlobalService.convertStringToDate("2022-12-03"),
                                                        "ฉีดยาพิษสุนัขบ้า",20);
        Bill bill = new Bill();
        bill.setStartDate(GlobalService.getCurrentTime());
        treatmentHistory.setBill(bill);
        bill.setTreatmentHistory(treatmentHistory);
        treatmentHistoryService.save(treatmentHistory);
        bill = mainBillService.findByBillID(1);
        System.out.println("--Bill--"+bill);
        System.out.println("--treatment--"+treatmentHistoryService.findByTreatmentHisID(1));

        medicine = medicineService.findByMedID(4);
        wareHouseService.createBillMedAndRemoveStock(bill,medicine,1,medicine.getDescription());
        Tool tool = toolService.findByToolID(1);
        wareHouseService.createBillToolAndRemoveStock(bill,tool,1);

//        - - - - - - - - - - - - - - - - - - - - -

        treatmentHistory = new TreatmentHistory(petService.findByPetID(1), GlobalService.convertStringToDate("2023-01-14"),
                                        "ถ่ายพยาธิ",21.7);
        bill = new Bill();
        bill.setStartDate(GlobalService.getCurrentTime());
        treatmentHistory.setBill(bill);
        bill.setTreatmentHistory(treatmentHistory);
        treatmentHistoryService.save(treatmentHistory);
        bill = mainBillService.findByBillID(2);
        System.out.println("--Bill--"+bill);
        System.out.println("--treatment--"+treatmentHistoryService.findByTreatmentHisID(2));
        medicine = medicineService.findByMedID(1);
        wareHouseService.createBillMedAndRemoveStock(bill,medicine,2,medicine.getDescription());
        medicine = medicineService.findByMedID(3);
        wareHouseService.createBillMedAndRemoveStock(bill,medicine,1,medicine.getDescription());
        tool = toolService.findByToolID(1);
        wareHouseService.createBillToolAndRemoveStock(bill,tool,1);

    //        ------------- Appointment --------------

        pet = petService.findByPetID(1);
        Appointment appointment = new Appointment(pet,GlobalService.convertStringToDate("2023-03-27"),"morning","ดูแผล");
        appointmentService.save(appointment);
        pet = petService.findByPetID(1);
        appointment = new Appointment(pet,GlobalService.convertStringToDate("2023-03-27"),"morning","vaccine");
        appointmentService.save(appointment);



    }
}