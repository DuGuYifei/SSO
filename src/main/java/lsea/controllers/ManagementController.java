package lsea.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.Api;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lsea.dto.GenerateReportDto;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.errors.ValidationError;
import lsea.service.LogService;
import lsea.service.ManagementService;
import lsea.utils.ListResult;
import lsea.utils.SuccessResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is responsible for handling requests related to the
 * statistics and analysis.
 */
@Api("management")
@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {

  /**
   * The LogService attribute is used to access the LogService methods.
   */
  @Autowired
  private LogService logService;

  /**
   * The ManagementService attribute is used to access the LogService methods.
   */
  private final ManagementService managementService;

  /**
   * The request meter registry of the application.
   */
  private final MeterRegistry requestMeterRegistry;

  /**
   * The constructor of the ManagementController class.
   *
   * @param managementService    ManagementService
   * @param requestMeterRegistry request MeterRegistry
   */
  public ManagementController(ManagementService managementService, MeterRegistry requestMeterRegistry) {
    this.managementService = managementService;
    this.requestMeterRegistry = requestMeterRegistry;
  }

  /**
   * The analysis method is used to get the five longest logs.
   *
   * @param numThreads int
   * @param request    HttpServletRequest containing the token cookie
   * @return ListResult object containing the five longest logs
   * @throws ValidationError       if the dto is not valid
   * @throws GenericForbiddenError if the user does not have the permission
   * @throws InterruptedException  if the thread is interrupted
   * @throws GenericNotFoundError  if the user is not found
   */
  /* Requirement 4.1 */
  @GetMapping("/analysis-longest-five")
  public ListResult analysisLongest(
      @RequestParam int numThreads,
      HttpServletRequest request)
      throws ValidationError, GenericForbiddenError, InterruptedException, GenericNotFoundError {
    requestMeterRegistry.counter("request.count").increment();
    requestMeterRegistry.counter("request.count", "method", "GET").increment();
    requestMeterRegistry.counter("request.count", "controller", "ManagementController").increment();

    String token = ValidationRouter.getTokenFromRequest(request);

    return managementService.longestFiveLogs(token, numThreads, null);
  }

  /**
   * The analysis method is used to get the five shortest logs.
   *
   * @param numThreads int
   * @param request    HttpServletRequest containing the token cookie
   * @return ListResult object containing the five shortest logs
   * @throws ValidationError       if the dto is not valid
   * @throws GenericForbiddenError if the user does not have the permission
   * @throws InterruptedException  if the thread is interrupted
   * @throws GenericNotFoundError  if the user is not found
   */
  /* Requirement 4.1 */
  @GetMapping("/analysis-shortest-five")
  public ListResult analysisShortest(
      @RequestParam int numThreads,
      HttpServletRequest request)
      throws ValidationError, GenericForbiddenError, InterruptedException, GenericNotFoundError {
    requestMeterRegistry.counter("request.count").increment();
    requestMeterRegistry.counter("request.count", "method", "GET").increment();
    requestMeterRegistry.counter("request.count", "controller", "ManagementController").increment();

    String token = ValidationRouter.getTokenFromRequest(request);

    return managementService.shortestFiveLogs(token, numThreads, null);
  }

  /**
   * The generateReport method is used to generate a report.
   *
   * @param dto     GenerateReportDto
   * @param request HttpServletRequest containing the token cookie
   * @return ResponseEntity containing the report
   * @throws GenericForbiddenError if the user does not have the permission
   * @throws ValidationError       if the dto is not valid
   * @throws InterruptedException  if the thread is interrupted
   * @throws GenericNotFoundError  if the user is not found
   */
  /* Requirement 4.4 */
  @PostMapping(value = "/report", produces = "application/vnd.ms-excel")
  public ResponseEntity<byte[]> generateReport(
      @RequestBody GenerateReportDto dto,
      HttpServletRequest request)
      throws GenericForbiddenError, ValidationError, InterruptedException, GenericNotFoundError {
    requestMeterRegistry.counter("request.count").increment();
    requestMeterRegistry.counter("request.count", "method", "POST").increment();
    requestMeterRegistry.counter("request.count", "controller", "ManagementController").increment();

    ValidationRouter.validate(dto);
    int iterations = dto.getIterations();
    String token = ValidationRouter.getTokenFromRequest(request);
    int maximumNumberOfThreads = dto.getNumThreads();

    List<Map<Integer, String>> result = managementService.reportCalculator(
        maximumNumberOfThreads,
        iterations,
        token);

    Map<Integer, String> resultLongest = result.get(0);
    Map<Integer, String> resultShortest = result.get(1);

    Workbook workbook = managementService.generateReport(
        dto,
        resultLongest,
        resultShortest,
        iterations);

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      workbook.write(bos);
      workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(
        MediaType.parseMediaType("application/vnd.ms-excel"));
    headers.setContentDispositionFormData("attachment", "report.xlsx");
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    ResponseEntity<byte[]> response = new ResponseEntity<>(
        bos.toByteArray(),
        headers,
        HttpStatus.OK);
    return response;
  }

  /**
   * The analysis method is used to get the five most frequent logs.
   *
   * @param logsNumber int number of logs to generate
   * @param request    HttpServletRequest containing the token cookie
   * @return status 200 if the request is successful
   * @throws GenericForbiddenError if the user does not have the permission
   * @throws GenericNotFoundError  if the user is not found
   * @throws ValidationError       if the dto is not valid
   */
  @GetMapping("/perform-test-on-database")
  public ResponseEntity<SuccessResult> testDatabase(@RequestParam("logsNumber") int logsNumber,
      HttpServletRequest request) throws GenericForbiddenError, GenericNotFoundError, ValidationError {
    String token = ValidationRouter.getTokenFromRequest(request);

    Map<String, Object> results = logService.generateTestData(logsNumber, token);

    SuccessResult result = SuccessResult.builder()
        .data(results.toString())
        .status(200)
        .build();

    return ResponseEntity.ok().body(result);
  }
}
