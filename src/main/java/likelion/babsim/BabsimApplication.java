package likelion.babsim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class BabsimApplication {

	public static void main(String[] args) {
		SpringApplication.run(BabsimApplication.class, args);
	}

}
