package lsea.controllers;

import io.swagger.annotations.Api;
import lsea.dto.CreateLogDto;
import lsea.errors.GenericForbiddenError;
import lsea.errors.ValidationError;
import lsea.service.LogService;
import lsea.service.UserService;
import lsea.utils.SuccessResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
     * The LogController constructor.
     * @param logService LogService
     */
    public LogController(LogService logService){
        this.logService = logService;
    }

    /**
     * The createOne method is used to create a new log.
     * @param dto CreateLogDto
     * @param request HttpServletRequest containing the token cookie
     * @return ResponseEntity object containing { status: 200, success: true }
     * @throws GenericForbiddenError if the cookie is not found or token is not valid
     * @throws ValidationError if the request body is invalid
     */
    @PostMapping
    public ResponseEntity<SuccessResult> createOne(@RequestBody CreateLogDto dto, HttpServletRequest request) throws GenericForbiddenError, ValidationError {
        ValidationRouter.validate(dto);

        String token = ValidationRouter.getTokenFromRequest(request);

        logService.createOne(dto, token);

        SuccessResult result = SuccessResult.builder().status(200).build();
        return ResponseEntity.ok(result);
    }

}
