package kr.ac.skuniv.realestate.controller;

import kr.ac.skuniv.realestate.RealestateRunner;
import kr.ac.skuniv.realestate.domain.dto.ConditionDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.entity.Forsale;
import kr.ac.skuniv.realestate.repository.ForsaleRepository;
import kr.ac.skuniv.realestate.service.ConditionService;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;

@RestController
@RequestMapping(value = "realestate/condition/*")
public class ConditionController {
    private final ConditionService conditionService;
    private boolean isEmptyInHashmap = true;
    /*
        1. map에서 name 값을 서울특별시중구로 줄지. 아니면 city: 서울특별시, distinct: 중구로 줄까
        2. 파일 입출력.
     */

    @Autowired
    public ConditionController(ConditionService conditionService){
        this.conditionService = conditionService;
    }

    @GetMapping("/{region}")
    public List<Forsale> onlyRegion(@PathVariable String region){
        int code = conditionService.convertRegionToCode(region);

        return conditionService.getTest(code);
    }
    @GetMapping("/city/{city}")
    public ConditionDto onlyCity(@PathVariable String city) {
        int code = conditionService.convertRegionToCode(city);
        ConditionDto conditionDto = new ConditionDto();
        List<GraphDto> graphDto = new ArrayList<>();



        conditionDto.setGraphDtos(graphDto);
        return conditionDto;
    }


    @GetMapping("/{region}/{term}")
    public String termRegion(@PathVariable String region, @PathVariable String term){
        System.out.println(region+term);
        return region + term;
    }

    @GetMapping("/test/{city}")
    public List<GraphDto> testExcel(@PathVariable String city){
        List<GraphDto> graphDtos = conditionService.convertEntit2Dto(conditionService.convertRegionToCode(city));

        return graphDtos;

    }
}
