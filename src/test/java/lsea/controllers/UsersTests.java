package lsea.controllers;

import io.swagger.annotations.Api;
import lsea.LaboratoryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
                "http://localhost:" + port + "/api/v1/users/ping", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Pong from user controller!");
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
                "http://localhost:" + port + "/api/v1/users", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("User created!");
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
                "http://localhost:" + port + "/api/v1/users", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isEqualTo(
                "A validation error occured: email must match \"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$\"");
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
                "http://localhost:" + port + "/api/v1/users", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("User created!");

        response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/users", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isEqualTo("User with email testuser3@example.com already exists.");
    }
}
