package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication

//@ComponentScan({"org.springframework.data"})

public class BatchBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchBankApplication.class, args);
	}
}
