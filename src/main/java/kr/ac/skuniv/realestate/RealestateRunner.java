package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.service.ConditionService;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class RealestateRunner implements ApplicationRunner {

    private final DataSource dataSource;
    private final ExcelConverterUtill excelConverterUtill;
    private final ConditionService conditionService;
    private Logger logger = LoggerFactory.getLogger(RealestateRunner.class);

    public RealestateRunner(DataSource dataSource, ExcelConverterUtill excelConverterUtill, ConditionService conditionService) {
        this.dataSource = dataSource;
        this.excelConverterUtill = excelConverterUtill;
        this.conditionService = conditionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try(Connection connection = dataSource.getConnection()){
            logger.info(connection.getMetaData().getURL());
            logger.info(connection.getMetaData().getUserName());
        } catch (Exception e){
            logger.error(e.getMessage());
        }

        try{
            excelConverterUtill.ReadRegionCode();
            conditionService.setRegionCode(excelConverterUtill.getRegionCodeMap());
            System.out.println(excelConverterUtill.getRegionCodeMap().size());
        }catch (Exception e){
            System.out.println(e);
        }
    }
}