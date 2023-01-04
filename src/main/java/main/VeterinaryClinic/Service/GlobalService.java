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
    private CureHistoryService cureHistoryService;
    public static String getStringCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public static Date getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(formatter.format(date));
        return date;
    }
    public static Date convertStringToDate(String strDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(strDate);
            System.out.println(date);
            return date;
        }
        catch(Exception e){
            System.out.println("Cannot Convert String to Date");
        }
        return null;
    }

    public void mockData() {
        for (int i=1; i<=30; i++){
            accountService.create("Test"+i, "123",null);
        }
        //------------- Pet --------------
        Pet pet = new Pet("มินิ","เมีย",GlobalService.convertStringToDate("2020-11-03"),false,"สุนัข","ชิวาวา");
        petService.create(pet);
        pet = new Pet("แทมมี่","ผู้",GlobalService.convertStringToDate("2020-11-03"),false,"แมว","เปอร์เซีย");
        petService.create(pet);
        //------------- Bill --------------
        Bill bill = new Bill(pet);
        mainBillService.create(bill);

        //------------- Medicine ------------

        Medicine medicine = new Medicine("Previcox","tablet",75,"ครั้งละ 1 เม็ด หลังอาหารเย็น","57 mg");
        medicineService.create(medicine);
        wareHouseService.create(new WareHouse(medicine, 100,6500,GlobalService.convertStringToDate("2022-01-22")));
        wareHouseService.create(new WareHouse(medicine, 200,13000,GlobalService.convertStringToDate("2022-03-15")));

        medicine = new Medicine("Tramadol HCL","capsule",15,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","50 mg");
        medicineService.create(medicine);
        wareHouseService.create(new WareHouse(medicine, 120,1440,GlobalService.convertStringToDate("2022-01-27")));

        medicine = new Medicine("Toflex/Cephalexin","capsule",20,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","250mg");
        medicineService.create(medicine);
        wareHouseService.create(new WareHouse(medicine, 200,3600,GlobalService.convertStringToDate("2022-02-12")));

        medicine = new Medicine("Defensor","dose",85,"vaccine");
        medicineService.create(medicine);
        wareHouseService.create(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2022-01-22")));

        BillMedicine billMedicine = new BillMedicine(bill,medicine,10);
        billMedicineService.create(billMedicine);

        //------------- Tool ------------
        Tool toolData = new Tool("Syringe",10,"5 ml");
        toolService.create(toolData);
        wareHouseService.create(new WareHouse(toolData, 100,800,GlobalService.convertStringToDate("2022-01-12")));
        wareHouseService.create(new WareHouse(toolData, 300,24000,GlobalService.convertStringToDate("2022-12-30")));
        toolData = new Tool("Syringe",5,"3 ml");
        toolService.create(toolData);
        wareHouseService.create(new WareHouse(toolData, 150,6000,GlobalService.convertStringToDate("2022-01-12")));

        BillTool billTool = new BillTool(bill,toolData,1);
        billToolService.create(billTool);

        //------------- Service ------------
        Serving serviceData = new Serving("เปิดห้องผ่าตัด",300);
        servingService.create(serviceData);
        serviceData = new Serving("Doctor Fee",170);
        servingService.create(serviceData);

        BillServing billService = new BillServing(bill,serviceData,1);
        billServiceService.create(billService);

        //------------- Appointment --------------
        pet = new Pet("แจ็ค","ผู้",GlobalService.convertStringToDate("2021-08-12"),true,"แมว","เปอร์เซีย");
        petService.create(pet);

        Appointment appointment = new Appointment(pet,GlobalService.convertStringToDate("2022-12-27"),"เย็น","อาเจียนและท้องเสีย");
        appointmentService.create(appointment);

        appointment = new Appointment(pet,GlobalService.convertStringToDate("2023-02-08"),"เช้า","ฉีดวัคซีน");
        appointmentService.create(appointment);

        //------------- Cure History --------------
        CureHistory cureHistory = new CureHistory(pet,GlobalService.convertStringToDate("2022-12-10"),"พบเห็บหมัด",false);
        cureHistoryService.create(cureHistory);
    }
}