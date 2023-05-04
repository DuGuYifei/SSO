package lsea.controllers;


import io.swagger.annotations.Api;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.errors.ValidationError;
import lsea.service.ManagementService;
import lsea.utils.ListResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * This controller is responsible for handling requests related to the
 * statistics and analysis.
 */
@Api("management")
@RestController
@RequestMapping("/api/v1/management")
/* Requirement 4_5 */
public class ManagementController {

    /**
     * The ManagementService attribute is used to access the LogService methods.
     */
    private final ManagementService managementService;

    /**
     * The constructor of the ManagementController class.
     * @param managementService ManagementService
     */
    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    /**
     * The analysis method is used to get the longest five logs.
     * @param request HttpServletRequest containing the token cookie
     * @return ListResult object containing the longest five logs
     * @throws InterruptedException if the thread is interrupted
     */
    @GetMapping("/analysis")
    public ListResult analysis(HttpServletRequest request) throws ValidationError, GenericForbiddenError, InterruptedException, GenericNotFoundError {
        String token = ValidationRouter.getTokenFromRequest(request);

        return managementService.longestFiveLogs(token);
    }
}
