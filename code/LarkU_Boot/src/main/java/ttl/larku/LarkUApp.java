package ttl.larku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@Import({LarkUTestDataConfig.class, LarkURealDataConfig.class})
@EnableJpaRepositories
@EnableTransactionManagement
public class LarkUApp {

	public static void main(String[] args) {
		SpringApplication.run(LarkUApp.class, args);
		
	}
}

