package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.aop.AspectException;
import kr.ac.skuniv.realestate.repository.RegionCodeRepository;
import kr.ac.skuniv.realestate.service.GraphService;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class RealestateRunner implements ApplicationRunner {

    private final Logger logger = LogManager.getLogger(RealestateRunner.class);
    private final DataSource dataSource;
    private final ExcelConverterUtill excelConverterUtill;
    private final GraphService graphService;
    private final RegionCodeRepository regionCodeRepository;

    @Autowired
    AspectException aspectException;

    public RealestateRunner(DataSource dataSource, ExcelConverterUtill excelConverterUtill, GraphService graphService, RegionCodeRepository regionCodeRepository) {
        this.dataSource = dataSource;
        this.excelConverterUtill = excelConverterUtill;
        this.graphService = graphService;
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

        try {
            excelConverterUtill.ReadRegionCode();
            graphService.setRegionCodeHashmap(excelConverterUtill.getRegionCodeMap());
            aspectException.setRegionCodeHashmap(excelConverterUtill.getRegionCodeMap());
            logger.info("RegionCodeMap Size : " + excelConverterUtill.getRegionCodeMap().size());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

//        RegionCode regionCode = new RegionCode();
//        regionCode.setValue("서울특별시");
//        regionCode.setId("11");
//        regionCodeRepository.save(regionCode);
//
//        regionCode = regionCodeRepository.findById("11").get();
//        logger.info(regionCode.getValue());
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
//            count++;
//            regionCodeRepository.save(regionCode1);
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
    }
}