package lsea.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import lsea.utils.ListResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is responsible for handling requests related to the
 * application's index.
 */
@Api(tags = "index")
@RestController
public class IndexController {

  /**
   * The request meter registry of the application.
   */
  private final MeterRegistry requestMeterRegistry;

  /**
   * The IndexController constructor.
   *
   * @param requestMeterRegistry request MeterRegistry
   */
    public IndexController(MeterRegistry requestMeterRegistry) {
        this.requestMeterRegistry = requestMeterRegistry;
    }

  /**
   * A sanity check
   *
   * @return ListResult
   */
  @GetMapping
  @ApiOperation(value = "sanity check", response = ListResult.class)
  public ListResult sanityCheck() {
    requestMeterRegistry.counter("request.count").increment();
    requestMeterRegistry.counter("request.count", "method", "GET").increment();
    requestMeterRegistry.counter("request.count", "controller", "IndexController").increment();

    ListResult response = new ListResult();
    response.setMeta(new HashMap<>());
    response.getMeta().put("message", "Hello World!");
    return response;
  }
}
