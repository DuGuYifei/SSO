package lsea.controllers;

import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import lsea.dto.CreateWebsiteDto;
import lsea.service.WebsiteService;
import lsea.utils.SuccessResult;
import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  private WebsiteService websiteService;

  /**
   * The createOne method is used to create a new website.
   *
   * @param dto     CreateWebsiteDto
   * @param request HttpServletRequest containing the token cookie
   * @return ResponseEntity object containing { status: 200, success: true }
   * @throws Exception if the cookie is not found or token is not valid
   */
  @PostMapping
  public ResponseEntity<SuccessResult> createOne(
    @RequestBody CreateWebsiteDto dto,
    HttpServletRequest request
  ) throws Exception {
    ValidationRouter.validate(dto);
    String token = ValidationRouter.getTokenFromRequest(request);
    websiteService.createOne(dto, token);
    SuccessResult result = SuccessResult.builder().status(200).build();

    return ResponseEntity.ok(result);
  }
}
