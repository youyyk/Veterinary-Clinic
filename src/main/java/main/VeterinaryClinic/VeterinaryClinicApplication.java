package main.VeterinaryClinic;

import main.VeterinaryClinic.Model.*;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Repository.MedicineRepository;
import main.VeterinaryClinic.Repository.WareHouseRepository;
import main.VeterinaryClinic.Service.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
@SpringBootApplication
public class VeterinaryClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeterinaryClinicApplication.class, args);
	}

//	@Bean
//	ApplicationRunner applicationRunner(MedicineService medicineService){
//	  return args -> {
//
//	  };
//	 }

//	@Bean
//	public CommandLineRunner mappingDemo(MedicineService medicineService,
//										 WareHouseService wareHouseService,
//										 ToolService toolService) {
//		return args -> {
//
//			//Create medicine connect to warehouse
//			Medicine data = new Medicine("Previcox","tablet",75,"ครั้งละ 1 เม็ด หลังอาหารเย็น","57 mg");
//			medicineService.create(data);
//			wareHouseService.create(new WareHouse(data, 100,6500,"2022/01/22"));
//			wareHouseService.create(new WareHouse(data, 200,13000,"2022/03/15"));
//
//			data = new Medicine("Tramadol HCL","capsule",15,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","50 mg");
//			medicineService.create(data);
//			wareHouseService.create(new WareHouse(data, 120,1440,"2022/01/27"));
//
//			data = new Medicine("Toflex/Cephalexin","capsule",20,"ครั้งละ 1 เม็ด หลังอาหารเช้า เย็น","250mg");
//			medicineService.create(data);
//			wareHouseService.create(new WareHouse(data, 200,3600,"2022/02/12"));
//
//
//			data = new Medicine("Hepato Support","capsule",35,"ครั้งละ 1 เม็ด หลังอาหารย็น");
//			medicineService.create(data);
//			wareHouseService.create(new WareHouse(data, 100,3300,"2022/01/06"));
//
//			//Create tool connect to warehouse
//			Tool toolData = new Tool("Syringe",10,"5 ml");
//			toolService.create(toolData);
//			wareHouseService.create(new WareHouse(toolData, 100,800,"2022/01/12"));
//			wareHouseService.create(new WareHouse(toolData, 300,24000,"2022/12/30"));
//
//			toolData = new Tool("Syringe",5,"3 ml");
//			toolService.create(toolData);
//			wareHouseService.create(new WareHouse(toolData, 150,6000,"2022/01/12"));
//
//
//		};
//	}


//	@Bean
//	public CommandLineRunner mappingDemo(ServiceService serviceService){
//		return args -> {
//			Service serviceData = new Service("เปิดห้องผ่าตัด",300);
//			serviceService.create(serviceData);
//			serviceData = new Service("Doctor Fee",170);
//			serviceService.create(serviceData);
//
//
//		};
//	}

	@Bean
	public CommandLineRunner mappingDemo(PetService petService,BillService billService,BillMedicineService billMedicineService,
										 MedicineService medicineService,WareHouseService wareHouseService){
		return args -> {
			Pet pet = new Pet("มินิ","เมีย","2015/12/09",false,"สุนัข","ชิวาวา");
			petService.create(pet);
			Medicine medicine = new Medicine("Defensor","dose",85,"vaccine");
			medicineService.create(medicine);
			wareHouseService.create(new WareHouse(medicine, 100,8000,"2022/01/22"));
			Bill bill = new Bill(pet,"2022/11/10","Cash",85);
			billService.create(bill);
			BillMedicine billMedicine = new BillMedicine(bill,medicine,1);
			billMedicineService.create(billMedicine);

			pet = new Pet("แทมมี่","ผู้","2021/08/12",false,"แมว","เปอร์เซีย");
			petService.create(pet);




		};
	}

}
