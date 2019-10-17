package kr.ac.skuniv.realestate.repository.impl;

import kr.ac.skuniv.realestate.domain.dto.LocationDto;
import kr.ac.skuniv.realestate.domain.dto.MapLocationDto;
import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.dto.SearchResDto;
import kr.ac.skuniv.realestate.domain.entity.BargainDate;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.EAN;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class BargainDateRepositoryImplTest{

    @Autowired
    private BargainDateRepositoryImpl bargainDateRepository;

    @Test
    @Transactional
    public void test() {

        SearchReqDto searchReqDto = SearchReqDto.builder().address("고잔동").housingType("apart").paging(1).build();

        //List<SearchResDto> buildingByAddressAndHousingType = bargainDateRepository.getBuildingByAddressAndHousingType(searchReqDto);

        List<BargainDate> buildingByAddressAndHousingType = bargainDateRepository.getBuildingByAddressAndHousingType(searchReqDto);
        log.warn(buildingByAddressAndHousingType.get(0).getBuilding().getArea());

        //log.warn(buildingByAddressAndHousingType.size());

//        buildingByAddressAndHousingType.forEach(building -> {
//            log.warn(building.toString());
//        });

    }

//        @Test
//        public void test(){
//            SearchReqDto searchReqDto = new SearchReqDto();
//            searchReqDto.setDealType(Arrays.asList(SearchReqDto.DealType.DEAL));
//            searchReqDto.setHousingType(Arrays.asList(SearchReqDto.HousingType.APART, SearchReqDto.HousingType.OPPICETEL));
//            searchReqDto.setMapLocation(
//                    new MapLocationDto(
//                            new LocationDto(BigDecimal.valueOf(20.0),BigDecimal.valueOf(20.0)),new LocationDto(BigDecimal.valueOf(25.0),BigDecimal.valueOf(26.0))
//                    )
//            );
//
//            List<SearchResDto> buildings = bargainDateRepository.getDealBuildingsByMapXYAndHousingType(searchReqDto);
//
//            log.info("size ===== " + buildings.size());
//            for (SearchResDto searchResDto : buildings){
//                //log.info(searchResDto.toString());
//                log.info("========" + searchResDto.getDealType());
//            }
//
////            List<SearchResDto> searchResTmpDtos = bargainDateRepository.getDealBuildingsByMapXYAndHousingType(searchReqDto);
////
////            List<SearchResDto> searchResDtos = new ArrayList<>();
////
////            HashMap<Integer, SearchReqDto> buildingMap = new HashMap<>();
//
////            for (SearchResDto searchResTmpDto : searchResTmpDtos){
////
////                if(buildingMap.containsKey(searchResTmpDto.getBuildingNum())) {
////                    buildingMap.get(searchReqDto).
////                }
////
////            }
//
//
//
//
//        }
}