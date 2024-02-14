package ca.mcgill.ecse321.fitnessplusplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FitnessplusplusApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessplusplusApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting() {
		return "Hello world!";
	

}
