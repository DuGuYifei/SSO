package lsea.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import lsea.dto.CreateWebsiteDto;
import lsea.dto.DeleteWebsiteDto;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.errors.ValidationError;
import lsea.service.WebsiteService;
import lsea.utils.SuccessResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is responsible for handling requests related to the
 * application's websites.
 */
@Api("Website controller")
@RestController
@RequestMapping("/api/v1/websites")
public class WebsiteController {

  /**
   * The websiteService attribute is used to access the WebsiteService methods.
   */
  private final WebsiteService websiteService;

  /**
   * The request meter registry of the application.
   */
  private final MeterRegistry requestMeterRegistry;

  /**
   * The WebsiteController constructor.
   * 
   * @param websiteService       WebsiteService
   * @param requestMeterRegistry request MeterRegistry
   */
  public WebsiteController(WebsiteService websiteService, MeterRegistry requestMeterRegistry) {
    this.websiteService = websiteService;
    this.requestMeterRegistry = requestMeterRegistry;
  }

  /**
   * The createOne method is used to create a new website.
   *
   * @param dto     CreateWebsiteDto
   * @param request HttpServletRequest containing the token cookie
   * @return ResponseEntity object containing { status: 200, success: true }
   * @throws GenericForbiddenError if the cookie is not found or token is not
   *                               valid
   * @throws ValidationError       if the request body is invalid or the cookie
   *                               not contains the token
   * @throws GenericNotFoundError  if the user is not found
   */
  @PostMapping
  public ResponseEntity<SuccessResult> createOne(
      @RequestBody CreateWebsiteDto dto,
      HttpServletRequest request) throws ValidationError, GenericForbiddenError, GenericNotFoundError {
    requestMeterRegistry.counter("request.count").increment();
    requestMeterRegistry.counter("request.count", "method", "POST").increment();
    requestMeterRegistry.counter("request.count", "controller", "WebsiteController").increment();

    ValidationRouter.validate(dto);
    String token = ValidationRouter.getTokenFromRequest(request);
    websiteService.createOne(dto, token);
    SuccessResult result = SuccessResult.builder().status(200).build();

    return ResponseEntity.ok(result);
  }

  /**
   * Delete the website by ID which is chosen by user in his own profile.
   *
   * @param dto     DeleteWebsiteDto
   * @param request HttpServletRequest containing the token cookie
   * @return ResponseEntity object containing { status: 200, success: true }
   * @throws GenericForbiddenError if the cookie is not found or token is not
   *                               valid
   * @throws ValidationError       if the request body is invalid or the cookie
   *                               not
   *                               contains the token
   * @throws GenericNotFoundError  if the user is not found
   */
  /* Requirement 7.6 */
  @PostMapping("/delete")
  public ResponseEntity<SuccessResult> deleteOne(
      @RequestBody DeleteWebsiteDto dto,
      HttpServletRequest request) throws ValidationError, GenericForbiddenError, GenericNotFoundError {
    requestMeterRegistry.counter("request.count").increment();
    requestMeterRegistry.counter("request.count", "method", "PUT").increment();
    requestMeterRegistry.counter("request.count", "controller", "WebsiteController").increment();

    ValidationRouter.validate(dto);
    String token = ValidationRouter.getTokenFromRequest(request);
    websiteService.deleteOne(dto, token);
    SuccessResult result = SuccessResult.builder().status(200).build();

    return ResponseEntity.ok(result);
  }
}
