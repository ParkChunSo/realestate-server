package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.domain.entity.RegionCode;
import kr.ac.skuniv.realestate.repository.RegionCodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RealestateApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    RegionCodeRepository regionCodeRepository;

    @MockBean
    RegionCode regionCode;

    private Logger logger = LoggerFactory.getLogger(RealestateApplicationTests.class);
    @Test
    public void contextLoads() {
        //regionCode = regionCodeRepository.findByCode(11);
        //logger.info(regionCode.getRegion());
    }

}
