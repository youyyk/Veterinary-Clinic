package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Model.Bill.BillTool;
import org.springframework.stereotype.Service;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillServiceService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


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
    public static Date convertStringToDate(String strDate) {
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

    public void createReport() {
        generateFileService.exportReport("accountReport");
    }

    public void mockData() {
        mainBillService.deleteBillByBillID(1);
        for (int i=1; i<=5; i++){
            accountService.create("Test"+i, "123",null);
        }
        //------------- Pet --------------
//        Pet pet = new Pet("มินิ","เมีย",GlobalService.convertStringToDate("2020-11-03"),false,"สุนัข","ชิวาวา");
//        petService.save(pet);
//        pet = new Pet("แทมมี่","ผู้",GlobalService.convertStringToDate("2020-11-03"),false,"แมว","เปอร์เซีย");
//        petService.save(pet);

        //        ------------- Bill --------------
//        TreatmentHistory treatmentHistory = new TreatmentHistory(petService.findByPetID(1), GlobalService.convertStringToDate("2020-11-03"),
//                "ฉีดยาพิษสุนัขบ้า",20,false);
//        Bill bill = new Bill();
//        treatmentHistory.setBill(bill);
//        bill.setTreatmentHistory(treatmentHistory);
//        treatmentHistoryService.save(treatmentHistory);
//        bill = mainBillService.findByBillID(1);
//        System.out.println("--Bill--"+bill);
//        System.out.println("--treatment--"+treatmentHistoryService.findByTreatmentHisID(1));

//        ------------- CureHistory --------------
//        TreatmentHistory treatmentHistory = new TreatmentHistory(petService.findByPetID(1),mainBillService.findByBillID(1),
//                GlobalService.convertStringToDate("2020-11-03"),"ฉีดยาพิษสุนัขบ้า",20,false);
//        treatmentHistoryService.save(treatmentHistory);
//        treatmentHistory = new TreatmentHistory(petService.findByPetID(1),mainBillService.findByBillID(1),
//                GlobalService.convertStringToDate("2020-12-08"),"ถ่ายพยาธิ",21.7,true);
//        treatmentHistoryService.save(treatmentHistory);
//        treatmentHistory = new TreatmentHistory(petService.findByPetID(1),mainBillService.findByBillID(1),
//                GlobalService.convertStringToDate("2020-12-08"),
//                "I bet you think about me when you're out",21.7,true);
//        treatmentHistoryService.save(treatmentHistory);


//        ------------- Medicine ------------

        Medicine medicine = new Medicine("Previcox","tablet",75,"ครั้งละ 1 เม็ด หลังอาหารเย็น","57 mg");
        medicineService.save(medicine);
        wareHouseService.create(new WareHouse(medicine, 100,6500,GlobalService.convertStringToDate("2022-01-22")));
        wareHouseService.create(new WareHouse(medicine, 200,13000,GlobalService.convertStringToDate("2023-03-15")));
        wareHouseService.create(new WareHouse(medicine, 150,8000,GlobalService.convertStringToDate("2023-05-20")));
        wareHouseService.create(new WareHouse(medicine, 200,13000,GlobalService.convertStringToDate("2023-10-06")));

        medicine = new Medicine("Tramadol HCL","capsule",15,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","50 mg");
        medicineService.save(medicine);
        wareHouseService.create(new WareHouse(medicine, 100,1440,GlobalService.convertStringToDate("2023-11-27")));
        wareHouseService.create(new WareHouse(medicine, 100,1440,GlobalService.convertStringToDate("2024-01-06")));
        wareHouseService.create(new WareHouse(medicine, 120,1440,GlobalService.convertStringToDate("2024-12-08")));

        medicine = new Medicine("Toflex/Cephalexin","capsule",20,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","250mg");
        medicineService.save(medicine);
        wareHouseService.create(new WareHouse(medicine, 50,500,GlobalService.convertStringToDate("2022-02-12")));
        wareHouseService.create(new WareHouse(medicine, 50,500,GlobalService.convertStringToDate("2023-04-17")));
        wareHouseService.create(new WareHouse(medicine, 100,1000,GlobalService.convertStringToDate("2023-06-11")));
        wareHouseService.create(new WareHouse(medicine, 200,2000,GlobalService.convertStringToDate("2023-09-02")));

        medicine = new Medicine("Defensor","dose",85,"vaccine");
        medicineService.save(medicine);
        wareHouseService.create(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2022-01-22")));
        wareHouseService.create(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2023-05-27")));
        wareHouseService.create(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2023-11-07")));
//
//
//        BillMedicine billMedicine = new BillMedicine(bill,medicineService.findByMedID(1),10);
//        billMedicineService.save(billMedicine);
//        billMedicine = new BillMedicine(bill,medicineService.findByMedID(3),1);
//        billMedicineService.save(billMedicine);
//
//        //------------- Tool ------------
        Tool toolData = new Tool("Syringe",10,"5 ml");
        toolService.save(toolData);
        wareHouseService.create(new WareHouse(toolData, 100,800,GlobalService.convertStringToDate("2022-01-12")));
        wareHouseService.create(new WareHouse(toolData, 300,24000,GlobalService.convertStringToDate("2023-04-02")));
        wareHouseService.create(new WareHouse(toolData, 300,24000,GlobalService.convertStringToDate("2023-07-12")));
        toolData = new Tool("Syringe",5,"3 ml");
        toolService.save(toolData);
        wareHouseService.create(new WareHouse(toolData, 150,6000,GlobalService.convertStringToDate("2023-03-12")));
        wareHouseService.create(new WareHouse(toolData, 150,6000,GlobalService.convertStringToDate("2023-07-01")));
//
//        BillTool billTool = new BillTool(bill,toolService.findByToolID(2),1);
//        billToolService.save(billTool);
////
////        //------------- Service ------------
        Serving serviceData = new Serving("เปิดห้องผ่าตัด",300);
        servingService.save(serviceData);
        serviceData = new Serving("Doctor Fee",170);
        servingService.save(serviceData);
//
//        BillServing billService = new BillServing(bill,servingService.findByServingID(1),1);
//        billServiceService.save(billService);
//
        //------------- Appointment --------------
//        pet = new Pet("แจ็ค","ผู้",GlobalService.convertStringToDate("2021-08-12"),true,"แมว","เปอร์เซีย");
//        petService.save(pet);
//
//        Appointment appointment = new Appointment(pet,GlobalService.convertStringToDate("2022-12-27"),"เย็น","อาเจียนและท้องเสีย");
//        appointmentService.create(appointment);
//
//        appointment = new Appointment(pet,GlobalService.convertStringToDate("2023-02-08"),"เช้า","ฉีดวัคซีน");
//        appointmentService.create(appointment);
//
//        //------------- Cure History --------------
//        CureHistory cureHistory = new CureHistory(pet,GlobalService.convertStringToDate("2022-12-10"),"พบเห็บหมัด",false);
//        cureHistoryService.create(cureHistory);
    }
}