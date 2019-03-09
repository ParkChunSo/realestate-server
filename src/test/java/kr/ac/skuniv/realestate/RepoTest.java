package kr.ac.skuniv.realestate;


import kr.ac.skuniv.realestate.controller.GraphController;
import kr.ac.skuniv.realestate.domain.dto.*;
import kr.ac.skuniv.realestate.domain.entity.Building;
import kr.ac.skuniv.realestate.repository.BuildingRepository;
import kr.ac.skuniv.realestate.service.GraphService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Log4j2
public class RepoTest {


//    @Autowired
//    BuildingRepository buildingRepository;
//
//    @Test
//    public void queryTest(){
//        SearchReqDto searchReqDto = new SearchReqDto();
//
//        LocationDto rigthTop = new LocationDto();
//        LocationDto leftBottom = new LocationDto();
//
//        rigthTop.setLatitude(new BigDecimal(25));
//        rigthTop.setLongitude(new BigDecimal(25));
//
//        leftBottom.setLatitude(new BigDecimal(10));
//        leftBottom.setLongitude(new BigDecimal(10));
//
//        MapLocationDto mapLocationDto = new MapLocationDto();
//        mapLocationDto.setLeftBottom(leftBottom);
//        mapLocationDto.setRightTop(rigthTop);
//
//        searchReqDto.setMapLocation(mapLocationDto);
//
//        SearchTmpDto searchTmpDto = new SearchTmpDto();
//
//        List<Building> buildings = buildingRepository.searchBuilding(searchReqDto);
//
//        searchTmpDto.setBuildings(buildings);
//
//        log.info(buildings.size());
//
//        LocationDto option1 = new LocationDto();
//        option1.setLatitude(new BigDecimal(18));
//        option1.setLongitude(new BigDecimal(18));
//
//        List<LocationDto> options = new ArrayList<>();
//
//        options.add(option1);
//
//        searchTmpDto.setOptions(options);
//
//        for (LocationDto option : searchTmpDto.getOptions()){
//            for (Building building : searchTmpDto.getBuildings()){
//                if(option.getLatitude().compareTo(building.getLatitude().add(new BigDecimal(-5))) == 1 &&
//                        option.getLatitude().compareTo(building.getLatitude().add(new BigDecimal(5))) == -1 &&
//                        option.getLongitude().compareTo(building.getLongitude().add(new BigDecimal(-5))) == 1 &&
//                            option.getLongitude().compareTo(building.getLongitude().add(new BigDecimal(5))) == -1){
//
//                    log.info("==============dddddd" + building.getName());
//                }
//                log.info("================" + building.getLatitude().add(new BigDecimal(-5)) );
//            }
//        }
//    }

    @Autowired
    GraphService graphService;

    @Test
    public void getGraph(){
//        RegionDto regionDto = RegionDto.builder()
//                .cityCode(regionCodeRepository.findById(city).get().getValue().substring(0, 2))
//                .regionType(RegionDto.RegionType.CITY)
//                .build();
        RegionDto regionDto = graphService.convertRegionToDto("서울특별시");
        log.info("city===============" + regionDto.getCityCode());
        DateDto dateDto = DateDto.builder()
                .dateType(DateDto.DateType.YEAR)
                .build();


        graphService.getGraphDtoByRegionDtoAndDateDto(regionDto, dateDto);

        List<GraphDto> graphDtos = graphService.getGraphDtos(regionDto, dateDto);

        Integer integer = new Integer(10);

        int a = 10;

        if(integer.equals(a)){
            log.info("equals ============");
        }

        //return graphService.getGraphDtos(regionDto, dateDto);


    }
}