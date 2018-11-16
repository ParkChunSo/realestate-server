package kr.ac.skuniv.realestate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/realestate/condition/*")
public class conditionController {

    @GetMapping("/timeRegion")
    public void timeRegion(){

    }
}
