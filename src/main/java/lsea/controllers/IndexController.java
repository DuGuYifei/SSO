package lsea.controllers;

import lsea.utils.ListResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class IndexController {

    @GetMapping
    public ListResult<Object> sanityCheck() {
        ListResult<Object> response = new ListResult<>();
        response.setMeta(new HashMap<>());
        response.getMeta().put("message", "Hello World!");
        return response;
    }
}
