package lsea.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.annotations.Api;
import lsea.LaboratoryApplication;
import lsea.utils.RandomBase64Generator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

/* Requirement 2.1 */
/**
 * This class contains integration tests for the User Controller.
 * It uses TestRestTemplate to send HTTP requests to the test server and
 * verifies the response.
 */
@Api(value = "User Integration Tests")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LaboratoryApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersTests {

  /**
   * TestRestTemplate is used to send HTTP requests to the test server.
   */
  @Autowired
  private TestRestTemplate restTemplate;

  /**
   * Port that is used by the test server.
   */
  @LocalServerPort
  private int port;

  /**
   * Following test verifies whether the users endpoint is alive
   */
  @Test
  public void testPing() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        "http://localhost:" + port + "/api/v1/users/ping",
        String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    String expected = "{\"data\":\"Pong from user controller!\",\"success\":true,\"status\":200}";
    assertThat(response.getBody()).isEqualTo(expected);
  }

  /**
   * Following test verifies whether the user creation endpoint works as expected.
   */
  @Test
  public void testCreateUser() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String requestBody = "{ \"username\": \"testUser_first\", \"password\": \"password123\", \"email\": \"testuser1@example.com\" }";
    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/users",
        request,
        String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    String expected = "{\"data\":null,\"success\":true,\"status\":200}";
    assertThat(response.getBody()).isEqualTo(expected);
  }

  /**
   * Following test verifies whether the invalid e-mail address is rejected.
   */
  @Test
  public void testCreateUserWithInvalidEmail() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String requestBody = "{ \"username\": \"testUser_second\", \"password\": \"password123\", \"email\": \"testuser2example.com\" }";
    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/users",
        request,
        String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    String expected = "{\"message\":\"A validation error occured: email must match \\\"^[a-z0-9_.+-]+@[a-z0-9-]+\\\\.[a-z0-9-.]+$\\\"\",\"success\":false,\"status\":403}";
    assertThat(response.getBody()).isEqualTo(expected);
  }

  /**
   * Following test verifies whether the user creation endpoint rejects
   * registering already existing users.
   */
  @Test
  public void testAlreadyExistingUser() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String requestBody = "{ \"username\": \"testUser_third\", \"password\": \"password123\", \"email\": \"testuser3@example.com\" }";
    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/users",
        request,
        String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/users",
        request,
        String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    String expected = "{\"message\":\"User with email testuser3@example.com already exists.\",\"success\":false,\"status\":409}";
    assertThat(response.getBody()).isEqualTo(expected);
  }

  @Test
  public void testAuthorizeUser() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // Create a user `testUser_fourth`
    String password = RandomBase64Generator.generateShort();
    String email = "testuser4@example.com";
    String requestBody = "{ \"username\": \"testUser_fourth\", \"password\": \"" +
        password +
        "\", \"email\": \"" +
        email +
        "\" }";
    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/users",
        request,
        String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    // Authorize the user
    requestBody = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";
    request = new HttpEntity<>(requestBody, headers);

    response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/users/authorize",
        request,
        String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    String cookieSet = response.getHeaders().getFirst("Set-Cookie");
    assertThat(cookieSet).isNotNull();
    assertThat(cookieSet).startsWith("token=");
  }

  @Test
  public void testBanAndUnBanUser() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String password = RandomBase64Generator.generateShort();
    String email = "test_user@example.com";
    String requestBody =
            "{ \"username\": \"test_user\", \"password\": \"" +
                    password +
                    "\", \"email\": \"" +
                    email +
                    "\" }";
    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/v1/users",
            request,
            String.class
    );

    password = RandomBase64Generator.generateShort();
    email = "test_user_not_admin@example.com";
    requestBody =
            "{ \"username\": \"test_user_not_admin\", \"password\": \"" +
                    password +
                    "\", \"email\": \"" +
                    email +
                    "\" }";
    request = new HttpEntity<>(requestBody, headers);

    response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/v1/users",
            request,
            String.class
    );

    String requestBodyNotAdmin =
            "{ \"email\": \"test_user_not_admin@example.com\", \"password\": \"" + password + "\" }";
    request = new HttpEntity<>(requestBodyNotAdmin, headers);

    response =
            restTemplate.postForEntity(
                    "http://localhost:" + port + "/api/v1/users/authorize",
                    request,
                    String.class
            );

    // Not admin user tries to ban another user

    String cookieSetNotAdmin = response.getHeaders().getFirst("Set-Cookie");

    headers.add("Cookie", cookieSetNotAdmin);
    requestBody =
            "{ \"emailAddress\": \"test_user@example.com\" }";
    request = new HttpEntity<>(requestBody, headers);

    response =
            restTemplate.postForEntity(
                    "http://localhost:" + port + "/api/v1/users/ban",
                    request,
                    String.class
            );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    // Actual admin user tries to ban another user

    String requestBodyAdmin =
            "{ \"email\": \"admin@example.com\", \"password\": \"adminadmin\" }";
    request = new HttpEntity<>(requestBodyAdmin, headers);

    response =
            restTemplate.postForEntity(
                    "http://localhost:" + port + "/api/v1/users/authorize",
                    request,
                    String.class
            );

    String cookieSetAdmin = response.getHeaders().getFirst("Set-Cookie");

    // Ban the user
    headers.add("Cookie", cookieSetAdmin);
    request = new HttpEntity<>(requestBody, headers);

    response =
            restTemplate.postForEntity(
                    "http://localhost:" + port + "/api/v1/users/ban",
                    request,
                    String.class
            );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    // Not admin user tries to unban another user
    headers.add("Cookie", cookieSetNotAdmin);
    request = new HttpEntity<>(requestBody, headers);

    response =
            restTemplate.postForEntity(
                    "http://localhost:" + port + "/api/v1/users/unban",
                    request,
                    String.class
            );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    // Actual admin user tries to unban another user
    headers.add("Cookie", cookieSetAdmin);
    request = new HttpEntity<>(requestBody, headers);

    response =
            restTemplate.postForEntity(
                    "http://localhost:" + port + "/api/v1/users/unban",
                    request,
                    String.class
            );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
