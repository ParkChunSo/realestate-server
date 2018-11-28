package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class RealestateRunner implements ApplicationRunner {

   //DataSource dataSource;
    ExcelConverterUtill excelConverterUtill;

    public RealestateRunner( ExcelConverterUtill excelConverterUtill) {
       // this.dataSource = dataSource;
        this.excelConverterUtill = excelConverterUtill;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        try(Connection connection = dataSource.getConnection()){
//            System.out.println(connection.getMetaData().getURL());
//            System.out.println(connection.getMetaData().getUserName());
//        } catch (Exception e){
//            System.out.println(e);
//        }

        excelConverterUtill.ReadRegionCode();

    }
}