package kr.ac.skuniv.realestate.controller;

import kr.ac.skuniv.realestate.repository.ForsaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "realestate/condition/*")
public class conditionController {

    @Autowired
    private ForsaleRepository forsaleRepository;

    @GetMapping("/{region}")
    public String onlyRegion(@PathVariable String region){
        System.out.println(region);
        return forsaleRepository.getCode(1122333) + "";
    }

    @GetMapping("/{region}/{term}")
    public String termRegion(@PathVariable String region, @PathVariable String term){
        System.out.println(region+term);
        return region + term;
    }
}
