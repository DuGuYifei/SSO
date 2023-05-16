package lsea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static lsea.tcp.TCPServerFunctions.startServer;

/* Requirement 2.5 */
/**
 * Application main class.
 */
@SpringBootApplication
public class LaboratoryApplication {

  /**
   * Application main entry point. Starts Spring Boot application context.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    System.setProperty("spring.profiles.active", "dev");
    SpringApplication.run(LaboratoryApplication.class, args);

    // Start the TCP server
    startServer();
  }
}
