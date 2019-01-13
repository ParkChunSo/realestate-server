package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.aop.AspectException;
import kr.ac.skuniv.realestate.service.ConditionService;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final ConditionService conditionService;
    private final AspectException aspectException;

    public RealestateRunner(DataSource dataSource, ExcelConverterUtill excelConverterUtill, ConditionService conditionService, AspectException aspectException) {
        this.dataSource = dataSource;
        this.excelConverterUtill = excelConverterUtill;
        this.conditionService = conditionService;
        this.aspectException = aspectException;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try(Connection connection = dataSource.getConnection()){
            logger.info("get URL : " + connection.getMetaData().getURL());
            logger.info(connection.getMetaData().getUserName());
        } catch (Exception e){
            logger.error(e.getMessage());
        }

        try{
            excelConverterUtill.ReadRegionCode();
            conditionService.setRegionCodeHashmap(excelConverterUtill.getRegionCodeMap());
            aspectException.setRegionCodeHashmap(excelConverterUtill.getRegionCodeMap());
            logger.info("RegionCodeMap Size : " + excelConverterUtill.getRegionCodeMap().size());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}