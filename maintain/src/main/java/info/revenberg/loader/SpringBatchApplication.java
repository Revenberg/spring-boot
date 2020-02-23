package info.revenberg.loader;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

// http://localhost:8099/invokejob
// http://localhost:8099/restart

@SpringBootApplication
@EnableAutoConfiguration // Spring Boot Auto Configuration
@EnableBatchProcessing
public class SpringBatchApplication {

	private static ConfigurableApplicationContext context;
	
	public static void main(String[] args) {
		context =  SpringApplication.run(SpringBatchApplication.class, args);
	}

	public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);
 
        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(SpringBatchApplication.class, args.getSourceArgs());
        });
 
        thread.setDaemon(false);
        thread.start();
    }
}