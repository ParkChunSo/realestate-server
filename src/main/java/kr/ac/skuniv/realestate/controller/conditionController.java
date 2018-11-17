package kr.ac.skuniv.realestate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "realestate/condition/*")
public class conditionController {

    @GetMapping("/{region}")
    public String onlyRegion(@PathVariable String region){
        System.out.println(region);
        return null;
    }

    @GetMapping("/{region}/{term}")
    public String termRegion(@PathVariable String region, @PathVariable String term){
        System.out.println(region+term);
        return null;
    }
}
