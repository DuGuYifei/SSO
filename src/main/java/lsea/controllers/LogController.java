package lsea.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import lsea.dto.CreateLogDto;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.errors.ValidationError;
import lsea.service.LogService;
import lsea.utils.SuccessResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is responsible for handling requests related to the
 * application's logs.
 */
@Api("log")
@RestController
@RequestMapping("/api/v1/logs")
public class LogController {

  /**
   * The logService attribute is used to access the LogService methods.
   */
  private final LogService logService;

  /**
   * The request meter registry of the application.
   */
    private final MeterRegistry requestMeterRegistry;

  /**
   * The LogController constructor.
   *
   * @param logService LogService
   * @param requestMeterRegistry request MeterRegistry
   */
  public LogController(LogService logService, MeterRegistry requestMeterRegistry) {
    this.logService = logService;
    this.requestMeterRegistry = requestMeterRegistry;
  }

  /**
   * The createOne method is used to create a new log.
   *
   * @param dto     CreateLogDto
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
      @RequestBody CreateLogDto dto,
      HttpServletRequest request) throws GenericForbiddenError, GenericNotFoundError, ValidationError {
    requestMeterRegistry.counter("request.count").increment();
    requestMeterRegistry.counter("request.count", "method", "POST").increment();
    requestMeterRegistry.counter("request.count", "controller", "LogController").increment();

    ValidationRouter.validate(dto);

    String token = ValidationRouter.getTokenFromRequest(request);

    logService.createOne(dto, token);

    SuccessResult result = SuccessResult.builder().status(200).build();
    return ResponseEntity.ok(result);
  }

  /**
   * Create N test logs into the logs table
   *
   * @param N       number of logs to create
   * @param request HttpServletRequest containing the token cookie
   * @return ResponseEntity object containing { status: 200, success: true }
   * @throws GenericForbiddenError if the cookie is not found or token is not
   *                               valid
   * @throws ValidationError       the cookie not contains the token
   * @throws GenericNotFoundError  if the user is not found
   */
  /* Requirement 4.3 */
  @PostMapping("/generate-test-data")
  public ResponseEntity<SuccessResult> generateData(
      @RequestBody int N,
      HttpServletRequest request) throws GenericForbiddenError, ValidationError, GenericNotFoundError {
    requestMeterRegistry.counter("request.count").increment();
    requestMeterRegistry.counter("request.count", "method", "POST").increment();
    requestMeterRegistry.counter("request.count", "controller", "LogController").increment();

    String token = ValidationRouter.getTokenFromRequest(request);

    for (int i = 0; i < N; i++) {
      CreateLogDto dto = CreateLogDto
          .builder()
          .data("test" + i)
          .logType(0)
          .build();
      logService.createOne(dto, token);
    }

    SuccessResult result = SuccessResult.builder().status(200).build();
    return ResponseEntity.ok(result);
  }
}
