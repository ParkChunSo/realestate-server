package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;

@Component
public class RealestateRunner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;
    private final ExcelConverterUtill excelConverterUtill;

    @Autowired
    public RealestateRunner(ExcelConverterUtill excelConverterUtill){
        this.excelConverterUtill = excelConverterUtill;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try(Connection connection = dataSource.getConnection()){
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());
        } catch (Exception e){
            System.out.println(e);
        }

        try{
            excelConverterUtill.ReadRegionCode();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}