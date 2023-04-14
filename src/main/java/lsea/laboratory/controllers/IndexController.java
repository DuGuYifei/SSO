package lsea.laboratory.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lsea.utils.ListResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@Api(tags = "index")
@RestController
public class IndexController {

    /**
     * A sanity check
     * @return ListResult
     */
    @GetMapping
    @ApiOperation(value = "sanity check", response = ListResult.class)
    public ListResult sanityCheck() {
        ListResult response = new ListResult();
        response.setMeta(new HashMap<>());
        response.getMeta().put("message", "Hello World!");
        return response;
    }
}
