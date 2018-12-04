package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class RealestateRunner implements ApplicationRunner {

    DataSource dataSource;
    ExcelConverterUtill excelConverterUtill;
    private Logger logger = LoggerFactory.getLogger(RealestateRunner.class);

    @Autowired
    public RealestateRunner(DataSource dataSource, ExcelConverterUtill excelConverterUtill) {
        this.dataSource = dataSource;
        this.excelConverterUtill = excelConverterUtill;
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
        }catch (Exception e){
            System.out.println(e);
        }
    }
}