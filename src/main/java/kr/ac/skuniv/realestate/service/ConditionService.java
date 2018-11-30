package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConditionService {

    ExcelConverterUtill excelConverterUtill;
    private Logger logger = LoggerFactory.getLogger(ConditionService.class);

    @Autowired
    public ConditionService(ExcelConverterUtill excelConverterUtill) {
        this.excelConverterUtill = excelConverterUtill;
    }

    public int translateCode(String region){
        logger.info(excelConverterUtill.getRegionCodeMap().size()+"");
        return excelConverterUtill.getRegionCodeMap().get(region);
    }
}
