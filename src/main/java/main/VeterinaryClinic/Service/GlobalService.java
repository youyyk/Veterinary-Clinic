package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillService;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillServiceService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GlobalService {
    @Autowired
    private static PetService petService;
    @Autowired
    private static MainBillService mainBillService;
    @Autowired
    private static BillMedicineService billMedicineService;
    @Autowired
    private static BillToolService billToolService;
    @Autowired
    private static BillServiceService billServiceService;
    @Autowired
    private static ToolService toolService;
    @Autowired
    private static ServiceService serviceService;
    @Autowired
    private static MedicineService medicineService;
    @Autowired
    private static WareHouseService wareHouseService;
    @Autowired
    private static AppointmentService appointmentService;
    @Autowired
    private static CureHistoryService cureHistoryService;
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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date date = formatter.parse(strDate);
            System.out.println(date);
            return date;
        }
        catch(Exception e){
            System.out.println("Cannot Convert String to Date");
        }
        return null;
    }

    public static void mockData() {
        //------------- Pet --------------
        Pet pet = new Pet("มินิ","เมีย",convertStringToDate("2020/11/03"),false,"สุนัข","ชิวาวา");
        petService.create(pet);
        pet = new Pet("แทมมี่","ผู้",convertStringToDate("2020/11/03"),false,"แมว","เปอร์เซีย");
        petService.create(pet);
        pet = new Pet("มินิ","เมีย",convertStringToDate("2015/12/09"),false,"สุนัข","ชิวาวา");
        petService.create(pet);

        //------------- Bill --------------
        Bill bill = new Bill(pet);
        mainBillService.create(bill);

        Medicine medicine = new Medicine("Defensor","dose",85,"vaccine");
        medicineService.create(medicine);
        wareHouseService.create(new WareHouse(medicine, 100,8000,convertStringToDate("2022/01/22")));

        BillMedicine billMedicine = new BillMedicine(bill,medicine,10);
        billMedicineService.create(billMedicine);

        Tool toolData = new Tool("Syringe",10,"5 ml");
        toolService.create(toolData);
        wareHouseService.create(new WareHouse(toolData, 100,800,convertStringToDate("2022/01/12")));
        wareHouseService.create(new WareHouse(toolData, 300,24000,convertStringToDate("2022/12/30")));
        toolData = new Tool("Syringe",5,"3 ml");
        toolService.create(toolData);
        wareHouseService.create(new WareHouse(toolData, 150,6000,convertStringToDate("2022/01/12")));

        BillTool billTool = new BillTool(bill,toolData,1);
        billToolService.create(billTool);

        Service serviceData = new Service("เปิดห้องผ่าตัด",300);
        serviceService.create(serviceData);
        serviceData = new Service("Doctor Fee",170);
        serviceService.create(serviceData);

        BillService billService = new BillService(bill,serviceData,1);
        billServiceService.create(billService);

        //------------- Appointment --------------
        pet = new Pet("แทมมี่","ผู้",convertStringToDate("2021/08/12"),false,"แมว","เปอร์เซีย");
        petService.create(pet);

        Appointment appointment = new Appointment(pet,convertStringToDate("2022/12/27"),"อาเจียนและท้องเสีย");
        appointmentService.create(appointment);

        appointment = new Appointment(pet,convertStringToDate("2023/02/08"),"ฉีดวัคซีน");
        appointmentService.create(appointment);

        //------------- Cure History --------------
        CureHistory cureHistory = new CureHistory(pet,convertStringToDate("2022/12/10"),"พบเห็บหมัด",false);
        cureHistoryService.create(cureHistory);



    }


}