package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.domain.entity.RegionCode;
import kr.ac.skuniv.realestate.repository.RegionCodeRepository;
import kr.ac.skuniv.realestate.service.ConditionService;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RealestateRunner implements ApplicationRunner {

    private final Logger logger = LogManager.getLogger(RealestateRunner.class);
    private final DataSource dataSource;
    private final ExcelConverterUtill excelConverterUtill;
    private final ConditionService conditionService;
    private final RegionCodeRepository regionCodeRepository;

    public RealestateRunner(DataSource dataSource, ExcelConverterUtill excelConverterUtill, ConditionService conditionService, RegionCodeRepository regionCodeRepository) {
        this.dataSource = dataSource;
        this.excelConverterUtill = excelConverterUtill;
        this.conditionService = conditionService;
        this.regionCodeRepository = regionCodeRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try (Connection connection = dataSource.getConnection()) {
            logger.info("get URL : " + connection.getMetaData().getURL());
            logger.info(connection.getMetaData().getUserName());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

//        try {
//            excelConverterUtill.ReadRegionCode();
//            conditionService.setRegionCodeHashmap(excelConverterUtill.getRegionCodeMap());
//            System.out.println(excelConverterUtill.getRegionCodeMap().size());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        RegionCode regionCode = new RegionCode();
//        regionCode.setValue("서울특별시");
//        regionCode.setId("11");
//        regionCodeRepository.save(regionCode);
//
//        regionCode = regionCodeRepository.findById("11").get();
//        logger.info(regionCode.getValue());
//        excelConverterUtill.ReadRegionCode();
//        HashMap<String, String> codes = excelConverterUtill.getRegionCodeMap();
//        RegionCode regionCode1 = new RegionCode();
//        RegionCode regionCode2 = new RegionCode();
//
//        logger.info(codes.keySet().size());
//        int count = 0;
//        for (Map.Entry<String, String> entry : codes.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
//            regionCode1.setId(key);
//            regionCode1.setValue(value);
//            regionCode2.setId(value);
//            regionCode2.setValue(key);
//            count++;
//            regionCodeRepository.save(regionCode1);
//            regionCodeRepository.save(regionCode2);
//        }
//
//        List<RegionCode> regionCodes = (List<RegionCode>) regionCodeRepository.findAll();
//        logger.info(String.format("total select size => %s", regionCodes.size()));
//        List<String> selectCodeList = new ArrayList<>();
//        for (RegionCode regionCode : regionCodes) {
//            selectCodeList.add(regionCode.getId());
//        }
//        for (String code : codes.values()) {
//            if (!selectCodeList.contains(code)) {
//                logger.info(String.format("not enter data => %s", code));
//            }
//        }

//        regionCode2 = regionCodeRepository.findById("서울특별시").get();
//        regionCode1 = regionCodeRepository.findByCode("1100000000");
//        logger.info(regionCode2.getRegion());
//        logger.info(regionCode2.getCode());
//        logger.info(regionCode1.getId());
//
//        RegionCode regionCode1 = new RegionCode();
//        regionCode1 = regionCodeRepository.findById("경기도광주시초월읍").get();
//        logger.info(regionCode1.getValue());
    }
}