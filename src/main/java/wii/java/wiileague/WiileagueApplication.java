package wii.java.wiileague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"wii.java.wiileague.model", 
                               "wii.java.wiileague.repository", 
                               "wii.java.wiileague.controller",
							"wii.java.wiileague.service"})
public class WiileagueApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiileagueApplication.class, args);
		System.out.println("Wiileague App Started");
	}

}
