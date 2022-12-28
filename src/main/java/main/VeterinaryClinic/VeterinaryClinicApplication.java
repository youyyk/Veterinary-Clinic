package main.VeterinaryClinic;

import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillService;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Service.*;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillServiceService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import java.util.LinkedList;
import java.util.List;


@SpringBootApplication
public class VeterinaryClinicApplication {
	public static void main(String[] args) {
		SpringApplication.run(VeterinaryClinicApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(LineSendService lineSendService){
		return args -> {
			List<String> client = new LinkedList<>();
			List<String> message = new LinkedList<>();
			client.add("Ua740d4c84ff34de6a233ae21b0db03ab");
//			message.add("Hello Test");
//			lineSendService.sendMessageToClient(client, message);

		};
	}


//	@Bean
//	public CommandLineRunner mappingDemo(PetService petService,
//										 MainBillService mainBillService,
//										 BillMedicineService billMedicineService, BillToolService billToolService, BillServiceService billServiceService,
//										 ToolService toolService, ServiceService serviceService, MedicineService medicineService, WareHouseService wareHouseService,
//										 AppointmentService appointmentService,CureHistoryService cureHistoryService){
//		return args -> {
//		//------------- Pet --------------
//        Pet pet = new Pet("มินิ","เมีย",GlobalService.convertStringToDate("2020/11/03"),false,"สุนัข","ชิวาวา");
//        petService.create(pet);
//        pet = new Pet("แทมมี่","ผู้",GlobalService.convertStringToDate("2020/11/03"),false,"แมว","เปอร์เซีย");
//        petService.create(pet);
//        //------------- Bill --------------
//        Bill bill = new Bill(pet);
//        mainBillService.create(bill);
//
//		//------------- Medicine ------------
//
//		Medicine medicine = new Medicine("Previcox","tablet",75,"ครั้งละ 1 เม็ด หลังอาหารเย็น","57 mg");
//		medicineService.create(medicine);
//		wareHouseService.create(new WareHouse(medicine, 100,6500,GlobalService.convertStringToDate("2022/01/22")));
//		wareHouseService.create(new WareHouse(medicine, 200,13000,GlobalService.convertStringToDate("2022/03/15")));
//
//		medicine = new Medicine("Tramadol HCL","capsule",15,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","50 mg");
//		medicineService.create(medicine);
//		wareHouseService.create(new WareHouse(medicine, 120,1440,GlobalService.convertStringToDate("2022/01/27")));
//
//		medicine = new Medicine("Toflex/Cephalexin","capsule",20,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","250mg");
//		medicineService.create(medicine);
//		wareHouseService.create(new WareHouse(medicine, 200,3600,GlobalService.convertStringToDate("2022/02/12")));
//
//		medicine = new Medicine("Defensor","dose",85,"vaccine");
//		medicineService.create(medicine);
//		wareHouseService.create(new WareHouse(medicine, 100,8000,GlobalService.convertStringToDate("2022/01/22")));
//
//        BillMedicine billMedicine = new BillMedicine(bill,medicine,10);
//        billMedicineService.create(billMedicine);
//
//		//------------- Tool ------------
//        Tool toolData = new Tool("Syringe",10,"5 ml");
//        toolService.create(toolData);
//        wareHouseService.create(new WareHouse(toolData, 100,800,GlobalService.convertStringToDate("2022/01/12")));
//        wareHouseService.create(new WareHouse(toolData, 300,24000,GlobalService.convertStringToDate("2022/12/30")));
//        toolData = new Tool("Syringe",5,"3 ml");
//        toolService.create(toolData);
//        wareHouseService.create(new WareHouse(toolData, 150,6000,GlobalService.convertStringToDate("2022/01/12")));
//
//        BillTool billTool = new BillTool(bill,toolData,1);
//        billToolService.create(billTool);
//
//		//------------- Service ------------
//        Service serviceData = new Service("เปิดห้องผ่าตัด",300);
//        serviceService.create(serviceData);
//        serviceData = new Service("Doctor Fee",170);
//        serviceService.create(serviceData);
//
//        BillService billService = new BillService(bill,serviceData,1);
//        billServiceService.create(billService);
//
//        //------------- Appointment --------------
//        pet = new Pet("แจ็ค","ผู้",GlobalService.convertStringToDate("2021/08/12"),true,"แมว","เปอร์เซีย");
//        petService.create(pet);
//
//        Appointment appointment = new Appointment(pet,GlobalService.convertStringToDate("2022/12/27"),"เย็น","อาเจียนและท้องเสีย");
//        appointmentService.create(appointment);
//
//        appointment = new Appointment(pet,GlobalService.convertStringToDate("2023/02/08"),"เช้า","ฉีดวัคซีน");
//        appointmentService.create(appointment);
//
//        //------------- Cure History --------------
//        CureHistory cureHistory = new CureHistory(pet,GlobalService.convertStringToDate("2022/12/10"),"พบเห็บหมัด",false);
//        cureHistoryService.create(cureHistory);
//
//		};
//	}

}
