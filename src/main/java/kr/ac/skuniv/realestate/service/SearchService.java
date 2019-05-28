package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.LocationDto;
import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.dto.SearchResDto;
import kr.ac.skuniv.realestate.domain.dto.SearchTmpDto;
import kr.ac.skuniv.realestate.domain.entity.Building;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.repository.impl.BargainDateRepositoryImpl;
import kr.ac.skuniv.realestate.repository.impl.BuildingRepositoryImpl;
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
public class SearchService {

    private BuildingRepositoryImpl buildingRepository;


    public SearchService(BuildingRepositoryImpl buildingRepository){
        this.buildingRepository = buildingRepository;
    }

    public List<SearchTmpDto> buildingFiltering(SearchReqDto searchReqDtos){
        List<SearchTmpDto> overlapList = new ArrayList<>();
        List<SearchTmpDto> temp = new ArrayList<>();

        if(searchReqDtos.getDealType().contains(SearchReqDto.DealType.DEAL)){
            temp = buildingRepository.getDealBuildingsByMapXYAndHousingType(searchReqDtos);
            overlapList.addAll(setDealType(temp, SearchReqDto.DealType.DEAL));
        }
        if(searchReqDtos.getDealType().contains(SearchReqDto.DealType.LEASE)){
            temp = buildingRepository.getLeaseBuildingsByMapXYAndHousingType(searchReqDtos);
            overlapList.addAll(setDealType(temp, SearchReqDto.DealType.LEASE));
        }
        if(searchReqDtos.getDealType().contains(SearchReqDto.DealType.MONTH)){
            temp = buildingRepository.getRentBuildingsByMapXYAndHousingType(searchReqDtos);
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

        List<SearchTmpDto> searchTmpDtos = new ArrayList<>(currentMap.values());

        return searchTmpDtos;
    }

    public List<SearchTmpDto> optionFiltering(List<SearchTmpDto> searchTmpDtos, List<LocationDto> options){

        List<SearchTmpDto> searchResDtos = new ArrayList<>();

        for (LocationDto option : options){
            for (SearchTmpDto searchTmpDto : searchTmpDtos){
                if(option.getLatitude().compareTo(searchTmpDto.getLatitude().add(new BigDecimal(-0.0004))) == 1 &&
                        option.getLatitude().compareTo(searchTmpDto.getLatitude().add(new BigDecimal(0.0004))) == -1 &&
                        option.getLongitude().compareTo(searchTmpDto.getLongitude().add(new BigDecimal(-0.0004))) == 1 &&
                            option.getLongitude().compareTo(searchTmpDto.getLongitude().add(new BigDecimal(0.0004))) == -1){

                    searchResDtos.add(searchTmpDto);
                    log.info("==============dddddd" + searchTmpDto.getName());
                }
                log.info("================" + searchTmpDto.getLatitude().add(new BigDecimal(-5)) );
            }
        }

        return searchTmpDtos;
    }

    public List<SearchTmpDto> setDealType(List<SearchTmpDto> searchTmpDtos, SearchReqDto.DealType dealType){
        for(SearchTmpDto searchTmpDto : searchTmpDtos){
            searchTmpDto.setDealType(dealType.toString());
        }
        return searchTmpDtos;
    }

}
