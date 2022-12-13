package main.VeterinaryClinic;

import main.VeterinaryClinic.Service.LineSendService;
import org.springframework.boot.ApplicationRunner;
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
//			List<String> client = new LinkedList<>();
//			List<String> message = new LinkedList<>();
//			client.add("Ua740d4c84ff34de6a233ae21b0db03ab");
//			message.add("Hello Test");
//			lineSendService.sendMessageToClient(client, message);
		};
	}

}
