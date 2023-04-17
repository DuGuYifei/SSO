package lsea.controllers;

import io.swagger.annotations.Api;
import lsea.dto.CreateUserDto;
import lsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The UserController class provides endpoints related to the user entity.
 */
@Api("User controller")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * The ping method is used to test if the server is alive.
     *
     * @return ResponseEntity object containing "Pong from user controller!" message.
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong from user controller!");
    }

    /**
     * The createOne method is used to create a new user.
     *
     * @param dto CreateUserDto object containing the user data.
     * @return ResponseEntity object containing "User created!" message.
     * @throws Exception if any of the steps fail.
     */
    @PostMapping
    public ResponseEntity<String> createOne(@RequestBody CreateUserDto dto) throws Exception {
        ValidationRouter.validate(dto);
        userService.createOne(dto);
        return ResponseEntity.ok("User created!");
    }
}
