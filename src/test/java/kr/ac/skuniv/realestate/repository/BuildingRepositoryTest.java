package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.entity.Building;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class BuildingRepositoryTest {
    @Autowired
    BuildingRepository buildingRepository;

    @Test
    public void findByLatitudeBetweenAndLongitudeBetween() {
//        List<Building> buildings = buildingRepository.findByLatitudeBetweenAndLongitudeBetween(BigDecimal.valueOf(20), BigDecimal.valueOf(25), BigDecimal.valueOf(20), BigDecimal.valueOf(25));
//        System.out.println(buildings.size());

        SearchReqDto searchReqDto = SearchReqDto.builder().address("고잔동").housingType("apart").build();
//
//        List<Building> buildings = buildingRepository.searchBuildingTest(searchReqDto);
//
//
//        buildings.forEach(building -> {
//            log.warn(building.toString());
//        });
    }
}