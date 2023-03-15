package main.VeterinaryClinic;

import main.VeterinaryClinic.Repository.PetRepository;
import main.VeterinaryClinic.Service.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class VeterinaryClinicApplication {
	public static void main(String[] args) {
		SpringApplication.run(VeterinaryClinicApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(GlobalService globalService){
		return args -> {
//            globalService.mockData();
//			globalService.createReport();
		};
	}

}
