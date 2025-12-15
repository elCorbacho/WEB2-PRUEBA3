package cl.web2.prueba3.prueba3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Prueba3Application {

	public static void main(String[] args) {
		SpringApplication.run(Prueba3Application.class, args);
	}

}
