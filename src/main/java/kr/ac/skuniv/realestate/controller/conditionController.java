package kr.ac.skuniv.realestate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class conditionController {

    @GetMapping("/timeRegion")
    public String timeRegion(){
        System.out.println("===============");
        return "hello";
    }
}