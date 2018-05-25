package tr.com.agem.alfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("tr.com.agem.alfa")
public class AlfaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlfaServerApplication.class, args);
	}
	
	@Configuration
	@Profile("development")
	@ComponentScan(lazyInit = true)
	static class DevelopmentConfig {
		/*
		 * Activate lazy init for beans for development profile so that it can
		 * start up faster! This does not affect production environment.
		 */
	}
}
