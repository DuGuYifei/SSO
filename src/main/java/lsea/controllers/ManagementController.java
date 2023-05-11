package lsea.controllers;

import io.swagger.annotations.Api;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lsea.dto.GenerateReportDto;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.errors.ValidationError;
import lsea.service.ManagementService;
import lsea.utils.ListResult;
import org.apache.poi.ss.usermodel.Workbook;
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
   * The ManagementService attribute is used to access the LogService methods.
   */
  private final ManagementService managementService;

  /**
   * The constructor of the ManagementController class.
   *
   * @param managementService ManagementService
   */
  public ManagementController(ManagementService managementService) {
    this.managementService = managementService;
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
    String token = ValidationRouter.getTokenFromRequest(request);

    return managementService.longestFiveLogs(token, numThreads);
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
    String token = ValidationRouter.getTokenFromRequest(request);

    return managementService.shortestFiveLogs(token, numThreads);
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
    ValidationRouter.validate(dto);
    int iterations = dto.getIterations();
    String token = ValidationRouter.getTokenFromRequest(request);
    int maximumNumberOfThreads = dto.getNumThreads();

    Map<Integer, String> resultLongest = new HashMap<>();
    for (int numThreads = 1; numThreads <= maximumNumberOfThreads; numThreads++) {
      int sumOfDurations = 0;
      for (int i = 0; i < iterations; i++) {
        ListResult listResult = managementService.longestFiveLogs(
            token,
            numThreads);
        String duration = listResult.getMeta().get("duration").toString();
        sumOfDurations += Integer.parseInt(duration);
      }

      int average = sumOfDurations / iterations;
      // Assign it to the thread
      resultLongest.put(numThreads, String.valueOf(average));
    }

    Map<Integer, String> resultShortest = new HashMap<>();
    for (int numThreads = 1; numThreads <= maximumNumberOfThreads; numThreads++) {
      int sumOfDurations = 0;
      for (int i = 0; i < iterations; i++) {
        ListResult listResult = managementService.shortestFiveLogs(
            token,
            numThreads);
        String duration = listResult.getMeta().get("duration").toString();
        sumOfDurations += Integer.parseInt(duration);
      }

      int average = sumOfDurations / iterations;
      // Assign it to the thread
      resultShortest.put(numThreads, String.valueOf(average));
    }

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
}
