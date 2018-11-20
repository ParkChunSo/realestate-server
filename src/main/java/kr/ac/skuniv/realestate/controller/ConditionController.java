package kr.ac.skuniv.realestate.controller;

import kr.ac.skuniv.realestate.domain.entity.Forsale;
import kr.ac.skuniv.realestate.repository.ForsaleRepository;
import kr.ac.skuniv.realestate.service.ConditionService;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "realestate/condition/*")
public class ConditionController {

    @Autowired
    private ForsaleRepository forsaleRepository;

    @Autowired
    private ExcelConverterUtill excelConverterUtill;


    @GetMapping("/{region}")
    public List<Forsale> onlyRegion(@PathVariable String region){
        System.out.println(region);
        return forsaleRepository.getCode(1122333);
    }

    @GetMapping("/{region}/{term}")
    public String termRegion(@PathVariable String region, @PathVariable String term){
        System.out.println(region+term);
        return region + term;
    }

    @GetMapping("/start")
    public String testExcel(){
        HashMap<String, Integer> test = new HashMap<>();
        try {
            test = excelConverterUtill.ReadRegionCode();
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IOException e1){
            System.out.println(e1.getMessage());
        }
        System.out.println(test.get("서울특별시 중구 회현동").toString());

        return "test1";

    }
}
