package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.repository.CharterDateRepository;
import kr.ac.skuniv.realestate.repository.RegionCodeRepository;
import kr.ac.skuniv.realestate.repository.RentDateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GraphService {
    private Logger logger = LoggerFactory.getLogger(GraphService.class);
    private final BargainDateRepository bargainDateRepository;
    private final CharterDateRepository charterDateRepository;
    private final RentDateRepository rentDateRepository;

    /*  그래프 디티오 조회 */
    public List<GraphDto> getGraphDtos(RegionDto regionDto, DateDto dateDto) {

        Map<String, List<GraphTmpDto>> graphTmpDtoMap = getGraphTmpDtoMap(regionDto, dateDto); // 디비 조회 후 맵 형식 변환

        graphTmpDtoMap = setDealTypeOnGraphTmpDtoList(graphTmpDtoMap);  // 그래프 템프 디티오에 딜타입 지정

        List<GraphDto> graphDtoList = mergeGraphTmpDtoListToGraphDtoList(graphTmpDtoMap);  // 실제 반환 할 디티오로 변환

        return graphDtoList;
    }

    /* 날짜를 날짜 디티오로 변경 */
    public DateDto convertDateToDto(String date) {
        String[] splitDate = date.split("-");
        DateDto dateDto = null;

        if (splitDate.length == 1) {
            dateDto = new DateDto(LocalDate.of(Integer.parseInt(splitDate[0]), 1, 1), DateDto.DateType.MONTH);
        } else if (splitDate.length == 2) {
            dateDto = new DateDto(LocalDate.of(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), 1), DateDto.DateType.DAY);
        }
        return dateDto;

    }

    /* 디비 조회 후 그래프 템프 디티오 맵 가져오기 */
    public Map<String, List<GraphTmpDto>> getGraphTmpDtoMap(RegionDto regionDto, DateDto dateDto) {
        Map<String, List<GraphTmpDto>> graphTmpDtoMap = new HashMap<>();

        graphTmpDtoMap.put("bargain", bargainDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto));
        graphTmpDtoMap.put("charter", charterDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto));
        graphTmpDtoMap.put("rent", rentDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto));

        return graphTmpDtoMap;
    }

    /* 그래프 템프 디티오 맵에 딜타입 지정 */
    public Map<String, List<GraphTmpDto>> setDealTypeOnGraphTmpDtoList(Map<String, List<GraphTmpDto>> graphTmpDtoListMap) {

        Set<String> keySet = graphTmpDtoListMap.keySet();

        keySet.forEach(key -> {
            graphTmpDtoListMap.put(key, setDealType(graphTmpDtoListMap.get(key), key));
        });

        return graphTmpDtoListMap;
    }

    /* 그래프 템프 디티오에 딜타입 지정 */
    public List<GraphTmpDto> setDealType(List<GraphTmpDto> graphTmpDtoList, String key) {

        String dealType = "";

        switch (key){
            case "bargain" :
                dealType =  "매매";
                break;
            case "charter" :
                dealType =  "전세";
                break;
            case "rent" :
                dealType = "월세";
                break;
        }

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            graphTmpDto.setDealType(dealType);
        }

        return graphTmpDtoList;
    }

    /* 그래프 템프 디티오를 그래프 디티오로 변경 후 하나의 리스트로 합침 */
    public List<GraphDto> mergeGraphTmpDtoListToGraphDtoList(Map<String, List<GraphTmpDto>> graphTmpDtoMap) {

        List<GraphDto> graphDtoList = new ArrayList<>();

        Set<String> keySet = graphTmpDtoMap.keySet();

        for (String s : keySet) {
            graphDtoList.addAll(convertGraphTmpDtoListToGraphDtoList(graphTmpDtoMap.get(s)));
        }

        return graphDtoList;
    }

    /* 그래프 템프 디티오를 그래프 디티오로 변경 */
    public List<GraphDto> convertGraphTmpDtoListToGraphDtoList(List<GraphTmpDto> graphTmpDtoList) {

        if(graphTmpDtoList.size() <= 0){
            return new ArrayList<>();
        }

        List<GraphDto> graphDtoList = new ArrayList<>();
        String dealType = graphTmpDtoList.get(0).getDealType();
        String housingType = graphTmpDtoList.get(0).getHousingType();
        ArrayList<Double> averageList = new ArrayList<>();

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            if (dealType.equals(graphTmpDto.getDealType()) && housingType.equals(graphTmpDto.getHousingType())) {
                averageList.add(graphTmpDto.getAverage());
            } else {
                graphDtoList.add(GraphDto.builder().dealType(dealType).housingType(housingType).average(averageList).build());
                averageList = new ArrayList<>();
                dealType = graphTmpDto.getDealType();
                housingType = graphTmpDto.getHousingType();
                averageList.add(graphTmpDto.getAverage());
            }
        }

        graphDtoList.add(GraphDto.builder().dealType(dealType).housingType(housingType).average(averageList).build());
        return graphDtoList;
    }
}