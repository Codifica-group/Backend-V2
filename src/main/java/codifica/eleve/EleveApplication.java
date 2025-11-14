package codifica.eleve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class EleveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EleveApplication.class, args);
	}

}
