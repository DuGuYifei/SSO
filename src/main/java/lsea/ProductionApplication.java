package lsea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application main class for production environment.
 */
@SpringBootApplication
public class ProductionApplication {

    /**
     * Application main entry point. Starts Spring Boot application context. (Production Environment)
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LaboratoryApplication.class, args);

    }
}
