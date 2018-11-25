package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class ConditionService {

    ExcelConverterUtill excelConverterUtill;
    HashMap<String, Integer> codeMap;

    @Autowired
    public ConditionService(ExcelConverterUtill excelConverterUtill) {
        this.excelConverterUtill = excelConverterUtill;
        try {
            this.codeMap = excelConverterUtill.ReadRegionCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int translateCode(String region){

        return codeMap.get(region);

    }
}
