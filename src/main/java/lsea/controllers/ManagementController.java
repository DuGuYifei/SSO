package lsea.controllers;

import io.swagger.annotations.Api;
import lsea.dto.GenerateReportDto;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.errors.ValidationError;
import lsea.service.ManagementService;
import lsea.utils.ListResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    @GetMapping("/analysis")
    public ListResult analysis(@RequestParam int numThreads, HttpServletRequest request)
            throws ValidationError, GenericForbiddenError, InterruptedException, GenericNotFoundError {
        String token = ValidationRouter.getTokenFromRequest(request);

        return managementService.longestFiveLogs(token, numThreads);
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
    public ResponseEntity<byte[]> generateReport(@RequestBody GenerateReportDto dto, HttpServletRequest request)
            throws GenericForbiddenError, ValidationError, InterruptedException, GenericNotFoundError {
        ValidationRouter.validate(dto);
        String token = ValidationRouter.getTokenFromRequest(request);
        Map<Integer, String> result = new HashMap<>();
        for (int numThreads = 1; numThreads <= dto.getNumThreads(); numThreads++) {
            ListResult listResult = managementService.longestFiveLogs(token, numThreads);
            String duration = listResult.getMeta().get("duration").toString();
            result.put(numThreads, duration);
        }

        Workbook workbook = managementService.generateReport(dto, result);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData("attachment", "report.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(bos.toByteArray(), headers, HttpStatus.OK);
        return response;
    }
}
