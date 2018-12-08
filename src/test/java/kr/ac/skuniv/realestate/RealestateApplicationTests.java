package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.repository.ForsaleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RealestateApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    ForsaleRepository forsaleRepository;

    private Logger logger = LoggerFactory.getLogger(RealestateApplicationTests.class);
    @Test
    public void contextLoads() {

        //logger.info(forsaleRepository.findByCode(1100000));

    }

}
