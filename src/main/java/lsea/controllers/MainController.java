package lsea.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The main controller for the API version 1.
 */
@Api("Main API Controller")
@RestController
@RequestMapping("/api/v1")
public class MainController {

    /**
     * The user controller that handles requests related to users.
     */
    @Autowired
    private UserController userController;

    /**
     * Returns the user controller that handles requests related to users.
     *
     * @return The user controller.
     */
    @RequestMapping("/users")
    public UserController userController() {
        return userController;
    }
}
