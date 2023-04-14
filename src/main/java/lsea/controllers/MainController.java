package lsea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class MainController {

    @Autowired
    private UserController userController;

    @GetMapping("/users/ping")
    public ResponseEntity<String> ping() {
        return userController.ping();
    }

    @RequestMapping("/users")
    public UserController userController() {
        return userController;
    }
}
