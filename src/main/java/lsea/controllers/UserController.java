package lsea.controllers;

import io.swagger.annotations.Api;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lsea.dto.AuthorizeUserDto;
import lsea.dto.BanUserDto;
import lsea.dto.CreateUserDto;
import lsea.dto.UnBanUserDto;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.errors.ValidationError;
import lsea.service.UserService;
import lsea.utils.SuccessResult;
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
   * @return ResponseEntity object containing "Pong from user controller!"
   *         message.
   */
  @GetMapping("/ping")
  public ResponseEntity<SuccessResult> ping() {
    SuccessResult result = SuccessResult
        .builder()
        .data("Pong from user controller!")
        .status(200)
        .build();

    return ResponseEntity.ok(result);
  }

  /**
   * The createOne method is used to create a new user.
   *
   * @param dto CreateUserDto object containing the user data.
   * @return ResponseEntity object containing { status: 200, success: true }
   *         object.
   * @throws Exception if any of the steps fail.
   */
  @PostMapping
  public ResponseEntity<SuccessResult> createOne(
      @RequestBody CreateUserDto dto) throws Exception {
    ValidationRouter.validate(dto);
    userService.createOne(dto);
    SuccessResult result = SuccessResult.builder().status(200).build();
    return ResponseEntity.ok(result);
  }

  /**
   * Returns a JWT token for the user and sets the cookie token in the
   * response.
   *
   * @param dto      AuthorizeUserDto object containing the email and password of
   *                 the
   *                 user.
   * @param response HttpServletResponse object to set the cookie token.
   * @return ResponseEntity object containing { data: "token" } object.
   * @throws Exception if any of the steps fail.
   */
  @PostMapping(path = "/authorize", produces = "application/json")
  public ResponseEntity<SuccessResult> authorize(
      @RequestBody AuthorizeUserDto dto,
      HttpServletResponse response) throws Exception {
    ValidationRouter.validate(dto);
    String token = userService.authorize(dto);
    SuccessResult result = SuccessResult
        .builder()
        .status(200)
        .data(token)
        .build();

    // Set the token as a cookie in the response
    Cookie cookie = new Cookie("token", token);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(60 * 60 * 24 * 7); // set the cookie to expire in 1 week
    response.addCookie(cookie);
    return ResponseEntity.ok(result);
  }

  /**
   * The banUser method is used to ban a user.
   *
   * @param dto     BanUserDto object containing the user id.
   * @param request HttpServletRequest object containing the token.
   * @return ResponseEntity object containing { status: 200, success: true }
   *         object.
   * @throws GenericForbiddenError if the user does not have the required
   *                               permissions.
   * @throws GenericNotFoundError if the user is not found.
   * @throws ValidationError if the request body is invalid.
   */
  @PostMapping(path = "/ban")
  public ResponseEntity<SuccessResult> ban(
          @RequestBody BanUserDto dto,
          HttpServletRequest request
  ) throws GenericForbiddenError, GenericNotFoundError, ValidationError {
    ValidationRouter.validate(dto);

    String token = ValidationRouter.getTokenFromRequest(request);

    userService.ban(dto, token);

    SuccessResult result = SuccessResult.builder().status(200).build();
    return ResponseEntity.ok(result);
  }

  /**
   * The unBanUser method is used to unban a user.
   *
   * @param dto     UnBanUserDto object containing the user id.
   * @param request HttpServletRequest object containing the token.
   * @return ResponseEntity object containing { status: 200, success: true }
   *         object.
   * @throws GenericForbiddenError if the user does not have the required
   *                               permissions.
   * @throws GenericNotFoundError if the user is not found.
   * @throws ValidationError if the request body is invalid.
   */
  @PostMapping(path = "/unban")
  public ResponseEntity<SuccessResult> unBan(
          @RequestBody UnBanUserDto dto,
          HttpServletRequest request
  ) throws GenericForbiddenError, GenericNotFoundError, ValidationError {
    ValidationRouter.validate(dto);

    String token = ValidationRouter.getTokenFromRequest(request);

    userService.unBan(dto, token);

    SuccessResult result = SuccessResult.builder().status(200).build();
    return ResponseEntity.ok(result);
  }
}
