package info.revenberg.song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * This is the main Spring Boot application class. It configures Spring Boot, JPA, Swagger
 */

@SpringBootApplication
@EnableAutoConfiguration // Sprint Boot Auto Configuration
@ComponentScan(basePackages = "info.revenberg.song")
@ConfigurationPropertiesScan("info.revenberg.song.properties")
@EnableJpaRepositories("info.revenberg.song.dao.jpa") // To segregate Sqlite and JPA repositories.
public class Application extends SpringBootServletInitializer {

    private static final Class<Application> applicationClass = Application.class;

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
