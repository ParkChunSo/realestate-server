package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.LocationDto;
import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.dto.SearchTmpDto;
import kr.ac.skuniv.realestate.repository.impl.BuildingRepositoryImpl;
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

    private final BuildingRepositoryImpl buildingRepository;

    public List<SearchTmpDto> buildingFiltering(SearchReqDto searchReqDtoList){
        List<SearchTmpDto> overlapList = new ArrayList<>();
        List<SearchTmpDto> temp;

        if(searchReqDtoList.getDealType().contains(SearchReqDto.DealType.DEAL)){
            temp = buildingRepository.getDealBuildingsByMapXYAndHousingType(searchReqDtoList);
            overlapList.addAll(setDealType(temp, SearchReqDto.DealType.DEAL));
        }
        if(searchReqDtoList.getDealType().contains(SearchReqDto.DealType.LEASE)){
            temp = buildingRepository.getLeaseBuildingsByMapXYAndHousingType(searchReqDtoList);
            overlapList.addAll(setDealType(temp, SearchReqDto.DealType.LEASE));
        }
        if(searchReqDtoList.getDealType().contains(SearchReqDto.DealType.MONTH)){
            temp = buildingRepository.getRentBuildingsByMapXYAndHousingType(searchReqDtoList);
            overlapList.addAll(setDealType(temp, SearchReqDto.DealType.MONTH));
        }

        HashMap<Long, SearchTmpDto> currentMap = new HashMap<>();
        HashSet<Long> buildingSet = new HashSet<>();

        for(SearchTmpDto current : overlapList){
            if(!buildingSet.contains(current.getBuildingNo())){
                buildingSet.add(current.getBuildingNo());
                currentMap.put(current.getBuildingNo(), current);
            }
            else{
                if(currentMap.get(current.getBuildingNo()).getDate().compareTo(current.getDate()) < 0){
                    currentMap.replace(current.getBuildingNo(), current);
                }
            }
        }

        List<SearchTmpDto> searchTmpDtoList = new ArrayList<>(currentMap.values());

        return searchTmpDtoList;
    }

    public List<SearchTmpDto> optionFiltering(List<SearchTmpDto> searchTmpDtoList, List<LocationDto> options){

        List<SearchTmpDto> searchResDtoList = new ArrayList<>();

        for (LocationDto option : options){
            for (SearchTmpDto searchTmpDto : searchTmpDtoList){
                if(option.getLatitude().compareTo(searchTmpDto.getLatitude().add(new BigDecimal(-0.0004))) == 1 &&
                        option.getLatitude().compareTo(searchTmpDto.getLatitude().add(new BigDecimal(0.0004))) == -1 &&
                        option.getLongitude().compareTo(searchTmpDto.getLongitude().add(new BigDecimal(-0.0004))) == 1 &&
                            option.getLongitude().compareTo(searchTmpDto.getLongitude().add(new BigDecimal(0.0004))) == -1){

                    searchResDtoList.add(searchTmpDto);
                    log.info("==============dddddd" + searchTmpDto.getName());
                }
                log.info("================" + searchTmpDto.getLatitude().add(new BigDecimal(-5)) );
            }
        }

        return searchTmpDtoList;
    }

    public List<SearchTmpDto> setDealType(List<SearchTmpDto> searchTmpDtoList, SearchReqDto.DealType dealType){

        for(SearchTmpDto searchTmpDto : searchTmpDtoList){
            searchTmpDto.setDealType(dealType.toString());
        }
        return searchTmpDtoList;
    }

}