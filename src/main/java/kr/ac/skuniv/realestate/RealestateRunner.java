package kr.ac.skuniv.realestate;

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

    public RealestateRunner(DataSource dataSource, ExcelConverterUtill excelConverterUtill, ConditionService conditionService) {
        this.dataSource = dataSource;
        this.excelConverterUtill = excelConverterUtill;
        this.conditionService = conditionService;
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
            System.out.println(excelConverterUtill.getRegionCodeMap().size());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}