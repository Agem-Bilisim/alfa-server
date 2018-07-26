package tr.com.agem.alfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("tr.com.agem.alfa")
public class AlfaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlfaServerApplication.class, args);
		
	}
	
}
