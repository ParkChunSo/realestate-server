package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.service.ConditionService;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.LoggerFactory;
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
    @Autowired
    public RealestateRunner(DataSource dataSource, ExcelConverterUtill excelConverterUtill) {
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
            conditionService.setRegionCode(excelConverterUtill.getRegionCodeMap());
            System.out.println(excelConverterUtill.getRegionCodeMap().size());
        }catch (Exception e){
            System.out.println(e);
        }
    }
}