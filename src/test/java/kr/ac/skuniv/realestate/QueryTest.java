package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.repository.CharterDateRepository;
import kr.ac.skuniv.realestate.repository.RentDateRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;

/**
 * Created by youngman on 2019-01-18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class QueryTest {

    @Autowired
    BargainDateRepository bargainDateRepository;
    @Autowired
    CharterDateRepository charterDateRepository;
    @Autowired
    RentDateRepository rentDateRepository;

    private Logger logger = LoggerFactory.getLogger(QueryTest.class);
    private final String cityCode = "11";
    private final String groopCode = "140";
    private final String dongName = "소공동";
    private final LocalDate date = LocalDate.of(2017, 7, 5);
    private final LocalDate date2 = LocalDate.of(2017, 6, 5);

    /*@Test
    public void testCityAndYear() {

        List<GraphTmpDto> queryDslList = bargainDateRepository.getByCityCodeAndYear(cityCode);

        List<Object[]> jpqlList = bargainDateRepository.getByCityCodeAndDateOnYear(cityCode);
        List<GraphTmpDto> jpqlDtoList = setDealTypeOnGraphTmpDtos(jpqlList);

        logger.info("===========================QuertDSL TestStart==============================");

        for (GraphTmpDto dtos : queryDslList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================QuertDSL TestEnd================================");

        logger.info("===========================JPQL TestStart==============================");

        for (GraphTmpDto dtos : jpqlDtoList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================JPQL TestEnd================================");
    }

    @Test
    public void testGroopAndYear() {

        List<GraphTmpDto> queryDslList = bargainDateRepository.getByGroopCodeAndYear(cityCode, groopCode);

        List<Object[]> jpqlList = bargainDateRepository.getByGroopCodeAndDateOnYear(cityCode, groopCode);
        List<GraphTmpDto> jpqlDtoList = setDealTypeOnGraphTmpDtos(jpqlList);

        logger.info("===========================QuertDSL TestStart==============================");

        for (GraphTmpDto dtos : queryDslList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================QuertDSL TestEnd================================");

        logger.info("===========================JPQL TestStart==============================");

        for (GraphTmpDto dtos : jpqlDtoList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================JPQL TestEnd================================");
    }

    @Test
    public void testDongAndYear() {

        List<GraphTmpDto> queryDslList = bargainDateRepository.getByDongNameAndYear(cityCode, groopCode, dongName);

        List<Object[]> jpqlList = bargainDateRepository.getByDongNameAndDateOnYear(cityCode, groopCode, dongName);
        List<GraphTmpDto> jpqlDtoList = setDealTypeOnGraphTmpDtos(jpqlList);

        logger.info("===========================QuertDSL TestStart==============================");

        for (GraphTmpDto dtos : queryDslList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================QuertDSL TestEnd================================");

        logger.info("===========================JPQL TestStart==============================");

        for (GraphTmpDto dtos : jpqlDtoList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================JPQL TestEnd================================");
    }

    @Test
    public void testCityAndMonth() {

        List<GraphTmpDto> queryDslList = bargainDateRepository.getByCityCodeAndMonth(cityCode, date);

        List<Object[]> jpqlList = bargainDateRepository.getByCityCodeAndDateOnMonth(cityCode, date);
        List<GraphTmpDto> jpqlDtoList = setDealTypeOnGraphTmpDtos(jpqlList);

        logger.info("===========================QuertDSL TestStart==============================");

        for (GraphTmpDto dtos : queryDslList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================QuertDSL TestEnd================================");

        logger.info("===========================JPQL TestStart==============================");

        for (GraphTmpDto dtos : jpqlDtoList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================JPQL TestEnd================================");
    }

    @Test
    public void testCityAndDay() {

        List<GraphTmpDto> queryDslList = bargainDateRepository.getByCityCodeAndDay(cityCode, date);

        List<Object[]> jpqlList = bargainDateRepository.getByCityCodeAndDateOnDay(cityCode, date);
        List<GraphTmpDto> jpqlDtoList = setDealTypeOnGraphTmpDtos(jpqlList);

        logger.info("===========================QuertDSL TestStart==============================");

        for (GraphTmpDto dtos : queryDslList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================QuertDSL TestEnd================================");

        logger.info("===========================JPQL TestStart==============================");

        for (GraphTmpDto dtos : jpqlDtoList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================JPQL TestEnd================================");
    }*/

    @Test
    public void testDinamicQuery() {

        RegionDto regionDto = new RegionDto(cityCode, groopCode, RegionDto.RegionType.DISTRICT);
        DateDto dateDto = new DateDto(date, DateDto.DateType.MONTH);
        List<GraphTmpDto> queryDslList = charterDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);

//        List<Object[]> jpqlList = charterDateRepository.getByGroopCodeAndDateOnMonth(cityCode, groopCode, date);
        List<Object[]> jpqlList = null;
        List<GraphTmpDto> jpqlDtoList = convertObjectsToGraphDtos(jpqlList);

        logger.info("===========================QuertDSL TestStart==============================");

        for (GraphTmpDto dtos : queryDslList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================QuertDSL TestEnd================================");

        logger.info("===========================JPQL TestStart==============================");

        for (GraphTmpDto dtos : jpqlDtoList) {
            logger.info(dtos.toString());
        }

        logger.info("===========================JPQL TestEnd================================");

        Assert.assertThat(queryDslList.equals(jpqlDtoList), is(true));
    }


    public List<GraphTmpDto> convertObjectsToGraphDtos(List<Object[]> objects) {
        List<GraphTmpDto> graphTmpDtos = objects.stream().map(graphTmpDto -> new GraphTmpDto(null, (String) graphTmpDto[0],
                (Date) graphTmpDto[1], (Double) graphTmpDto[2])).collect(Collectors.toList());

        return graphTmpDtos;
    }

    @Test
    public void testDate() {
        logger.info(String.valueOf(date.getYear()));
        logger.info(String.valueOf(date.getDayOfYear()));

        String s = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String s2 = date2.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        logger.info(s);


        if (s.equals(s2)) {
            logger.info("같다");
        } else {
            logger.info("다르다");
        }
    }

}
