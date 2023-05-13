package lsea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static lsea.tcp.TCPServerFunctions.startServer;

/* Requirement 2.5 */
/**
 * Application main class for production environment.
 */
@SpringBootApplication
public class ProductionApplication {

  /**
   * Application main entry point. Starts Spring Boot application context.
   * (Production Environment)
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    System.setProperty("spring.profiles.active", "prod");
    SpringApplication.run(LaboratoryApplication.class, args);

    // Start the TCP server
    startServer();
  }
}
