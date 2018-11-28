package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class ConditionService {

    ExcelConverterUtill excelConverterUtill;
    HashMap<String, Integer> codeMap;

    public ConditionService(ExcelConverterUtill excelConverterUtill) {
        this.excelConverterUtill = excelConverterUtill;
        this.codeMap = excelConverterUtill.getRegionCodeMap();
    }

    public int test(String region){
        return codeMap.get(region);
    }

    public String makeRegion(String city, String district){
        return city + " " + district;
    }

    public String makeRegion(String city, String district, String neighborhood){
        return city + " " + district + " " +neighborhood;
    }

    public int translateCode(String region){
        return codeMap.get(region);
    }
}
