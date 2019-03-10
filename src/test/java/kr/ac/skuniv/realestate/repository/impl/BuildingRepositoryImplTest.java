//package kr.ac.skuniv.realestate.repository.impl;
//
//import kr.ac.skuniv.realestate.domain.dto.LocationDto;
//import kr.ac.skuniv.realestate.domain.dto.MapLocationDto;
//import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
//import kr.ac.skuniv.realestate.domain.dto.SearchTmpDto;
//import kr.ac.skuniv.realestate.service.SearchService;
//import lombok.extern.log4j.Log4j2;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.jws.Oneway;
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Log4j2
//public class BuildingRepositoryImplTest {
//    @Autowired
//    BuildingRepositoryImpl buildingRepository;
//
//    @Autowired
//    SearchService searchService;
//
//    @Test
//    public void test(){
//        SearchReqDto searchReqDto = new SearchReqDto();
//        searchReqDto.setDealType(Arrays.asList(SearchReqDto.DealType.DEAL));
//        searchReqDto.setHousingType(Arrays.asList(SearchReqDto.HousingType.APART, SearchReqDto.HousingType.OPPICETEL));
//        searchReqDto.setMapLocation(
//                new MapLocationDto(
//                        new LocationDto(BigDecimal.valueOf(25.0),BigDecimal.valueOf(26.0)),  new LocationDto(BigDecimal.valueOf(20.0),BigDecimal.valueOf(20.0))
//                )
//        );
//
//        //buildingRepository.getDealBuildingsByMapXYAndHousingType(searchReqDto);
//        //buildingRepository.getLeaseBuildingsByMapXYAndHousingType(searchReqDto);
//        //buildingRepository.getRentBuildingsByMapXYAndHousingType(searchReqDto);
//
//        List<SearchTmpDto> searchTmpDtos = searchService.buildingFiltering(searchReqDto);
//
//        for(SearchTmpDto searchTmpDto : searchTmpDtos){
//            log.info(searchTmpDto.toString());
//        }
//    }
//
//}