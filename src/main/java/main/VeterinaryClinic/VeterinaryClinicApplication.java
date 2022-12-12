package main.VeterinaryClinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class VeterinaryClinicApplication {
	public static void main(String[] args) {
		SpringApplication.run(VeterinaryClinicApplication.class, args);
	}

//	@Bean
//	ApplicationRunner applicationRunner(AccountService accountService){
//		return args -> {
//			accountService.create("first", "first");
//		};
//	}
}
