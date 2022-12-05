package main.VeterinaryClinic;

import main.VeterinaryClinic.Service.AccountService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VeterinaryClinicApplication {
	public static void main(String[] args) {
		SpringApplication.run(VeterinaryClinicApplication.class, args);
	}

//	@Bean
//	ApplicationRunner applicationRunner(AccountService accountService){
//		return args -> {
//			accountService.create("firstService4", "t", "customer");
//		};
//	}
}
