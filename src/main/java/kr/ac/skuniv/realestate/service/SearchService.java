package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.LocationDto;
import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.dto.SearchResDto;
import kr.ac.skuniv.realestate.domain.dto.SearchTmpDto;
import kr.ac.skuniv.realestate.domain.entity.BargainDate;
import kr.ac.skuniv.realestate.domain.entity.Building;
import kr.ac.skuniv.realestate.domain.entity.CharterDate;
import kr.ac.skuniv.realestate.domain.entity.RentDate;
import kr.ac.skuniv.realestate.repository.BuildingRepository;
import kr.ac.skuniv.realestate.repository.RegionCodeRepository;
import kr.ac.skuniv.realestate.repository.impl.BargainDateRepositoryImpl;
import kr.ac.skuniv.realestate.repository.impl.BuildingRepositoryImpl;
import kr.ac.skuniv.realestate.repository.impl.CharterDateRepositoryImpl;
import kr.ac.skuniv.realestate.repository.impl.RentDateRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by YoungMan on 2019-02-16.
 */

@Service @Log4j2
@RequiredArgsConstructor
public class SearchService {

    private final BuildingRepository buildingRepository;
    private final BargainDateRepositoryImpl bargainDateRepository;
    private final CharterDateRepositoryImpl charterDateRepository;
    private final RentDateRepositoryImpl rentDateRepository;
    private final RegionCodeRepository regionCodeRepository;

    public List<SearchResDto> getBuildingList(SearchReqDto searchReqDto) {

        List<SearchResDto> searchResDtoList = new ArrayList<>();

        switch (searchReqDto.getDeal()){
            case "bargain":
                List<BargainDate> bargainList = getBargainList(searchReqDto);
                searchResDtoList = buildSearchResDtoByBargainDto(bargainList);
                break;
            case "charter":
                List<CharterDate> charterList = getCharterList(searchReqDto);
                searchResDtoList = buildSearchResDtoByCharterDto(charterList);
                break;
            case "rent":
                List<RentDate> rentList = getRentList(searchReqDto);
                searchResDtoList = buildSearchResDtoByRentDto(rentList);
                break;
        }
        return searchResDtoList;
    }

    private List<BargainDate> getBargainList(SearchReqDto searchReqDto) {
        List<BargainDate> buildingByAddressAndHousingType = bargainDateRepository.getBuildingByAddressAndHousingType(searchReqDto);

        return buildingByAddressAndHousingType;
    }

    private List<CharterDate> getCharterList(SearchReqDto searchReqDto) {
        List<CharterDate> buildingByAddressAndHousingType = charterDateRepository.getBuildingByAddressAndHousingType(searchReqDto);

        return buildingByAddressAndHousingType;
    }

    private List<RentDate> getRentList(SearchReqDto searchReqDto) {
        List<RentDate> buildingByAddressAndHousingType = rentDateRepository.getBuildingByAddressAndHousingType(searchReqDto);

        return buildingByAddressAndHousingType;
    }

    private List<SearchResDto> buildSearchResDtoByBargainDto(List<BargainDate> bargainDates){
        List<SearchResDto> searchResDtos = new ArrayList<>();
        bargainDates.forEach(bargainDate -> {
            String city = String.valueOf(bargainDate.getBuilding().getCity());
            String groop = String.valueOf(bargainDate.getBuilding().getGroop());
            SearchResDto searchResDto = SearchResDto.builder()
                    .address(regionCodeRepository.findById(city + groop).get().getValue() + " dong")
                    .name(bargainDate.getBuilding().getName()).area(bargainDate.getBuilding().getArea())
                    .floor(bargainDate.getBuilding().getFloor()).constructorYear(bargainDate.getBuilding().getConstructYear())
                    .price(bargainDate.getPrice()).date(bargainDate.getDate()).type(bargainDate.getBuilding().getType()).build();

            searchResDtos.add(searchResDto);
        });

        return searchResDtos;
    }

    private List<SearchResDto> buildSearchResDtoByCharterDto(List<CharterDate> charterDates){
        List<SearchResDto> searchResDtos = new ArrayList<>();
        charterDates.forEach(charterDate -> {
            String city = String.valueOf(charterDate.getBuilding().getCity());
            String groop = String.valueOf(charterDate.getBuilding().getGroop());
            SearchResDto searchResDto = SearchResDto.builder()
                    .address(regionCodeRepository.findById(city + groop + "00").get().getValue() + " dong")
                    .name(charterDate.getBuilding().getName()).area(charterDate.getBuilding().getArea())
                    .floor(charterDate.getBuilding().getFloor()).constructorYear(charterDate.getBuilding().getConstructYear())
                    .price(String.valueOf(charterDate.getPrice())).date(charterDate.getDate()).type(charterDate.getBuilding().getType()).build();

            searchResDtos.add(searchResDto);
        });

        return searchResDtos;
    }

    private List<SearchResDto> buildSearchResDtoByRentDto(List<RentDate> rentDates){
        List<SearchResDto> searchResDtos = new ArrayList<>();
        rentDates.forEach(rentDate -> {
            String city = String.valueOf(rentDate.getBuilding().getCity());
            String groop = String.valueOf(rentDate.getBuilding().getGroop());
            SearchResDto searchResDto = SearchResDto.builder()
                    .address(regionCodeRepository.findById(city + groop + "00").get().getValue() + " dong")
                    .name(rentDate.getBuilding().getName()).area(rentDate.getBuilding().getArea())
                    .floor(rentDate.getBuilding().getFloor()).constructorYear(rentDate.getBuilding().getConstructYear())
                    .price(String.valueOf(rentDate.getMonthlyPrice())).deposit(String.valueOf(rentDate.getGuaranteePrice())).date(rentDate.getDate()).type(rentDate.getBuilding().getType()).build();

            searchResDtos.add(searchResDto);
        });

        return searchResDtos;
    }
}

//    public List<SearchResDto> getBuildingList(SearchReqDto searchReqDto){
//
//        List<Building> buildings = buildingRepository.searchBuildingTest(searchReqDto);
//
//
//        return buildings;
//    }
//

//    public List<SearchTmpDto> buildingFiltering(SearchReqDto searchReqDtoList){
//        List<SearchTmpDto> overlapList = new ArrayList<>();
//        List<SearchTmpDto> temp;
//
//        if(searchReqDtoList.getDealType().contains(SearchReqDto.DealType.DEAL)){
//            temp = buildingRepository.getDealBuildingsByMapXYAndHousingType(searchReqDtoList);
//            overlapList.addAll(setDealType(temp, SearchReqDto.DealType.DEAL));
//        }
//        if(searchReqDtoList.getDealType().contains(SearchReqDto.DealType.LEASE)){
//            temp = buildingRepository.getLeaseBuildingsByMapXYAndHousingType(searchReqDtoList);
//            overlapList.addAll(setDealType(temp, SearchReqDto.DealType.LEASE));
//        }
//        if(searchReqDtoList.getDealType().contains(SearchReqDto.DealType.MONTH)){
//            temp = buildingRepository.getRentBuildingsByMapXYAndHousingType(searchReqDtoList);
//            overlapList.addAll(setDealType(temp, SearchReqDto.DealType.MONTH));
//        }
//
//        HashMap<Long, SearchTmpDto> currentMap = new HashMap<>();
//        HashSet<Long> buildingSet = new HashSet<>();
//
//        for(SearchTmpDto current : overlapList){
//            if(!buildingSet.contains(current.getBuildingNo())){
//                buildingSet.add(current.getBuildingNo());
//                currentMap.put(current.getBuildingNo(), current);
//            }
//            else{
//                if(currentMap.get(current.getBuildingNo()).getDate().compareTo(current.getDate()) < 0){
//                    currentMap.replace(current.getBuildingNo(), current);
//                }
//            }
//        }
//
//        List<SearchTmpDto> searchTmpDtoList = new ArrayList<>(currentMap.values());
//
//        return searchTmpDtoList;
//    }
//
//    public List<SearchTmpDto> optionFiltering(List<SearchTmpDto> searchTmpDtoList, List<LocationDto> options){
//
//        List<SearchTmpDto> searchResDtoList = new ArrayList<>();
//
//        for (LocationDto option : options){
//            for (SearchTmpDto searchTmpDto : searchTmpDtoList){
//                if(option.getLatitude().compareTo(searchTmpDto.getLatitude().add(new BigDecimal(-0.0004))) == 1 &&
//                        option.getLatitude().compareTo(searchTmpDto.getLatitude().add(new BigDecimal(0.0004))) == -1 &&
//                        option.getLongitude().compareTo(searchTmpDto.getLongitude().add(new BigDecimal(-0.0004))) == 1 &&
//                            option.getLongitude().compareTo(searchTmpDto.getLongitude().add(new BigDecimal(0.0004))) == -1){
//
//                    searchResDtoList.add(searchTmpDto);
//                    log.info("==============dddddd" + searchTmpDto.getName());
//                }
//                log.info("================" + searchTmpDto.getLatitude().add(new BigDecimal(-5)) );
//            }
//        }
//
//        return searchTmpDtoList;
//    }
//
//    public List<SearchTmpDto> setDealType(List<SearchTmpDto> searchTmpDtoList, SearchReqDto.DealType dealType){
//
//        for(SearchTmpDto searchTmpDto : searchTmpDtoList){
//            searchTmpDto.setDealType(dealType.toString());
//        }
//        return searchTmpDtoList;
//    }

//}