package demo.spring.boot.demospringboot.controller.mankan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class MankanController {

    @GetMapping(value="/free-marker")
    public String freeMarker(Map<String,Object> map,
                             @RequestParam(name="url") String url){
        return "mankan";
    }

}
