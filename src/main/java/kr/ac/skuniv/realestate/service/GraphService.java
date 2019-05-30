package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.repository.CharterDateRepository;
import kr.ac.skuniv.realestate.repository.RentDateRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
//@RequiredArgsConstructor
public class GraphService {
    private Logger logger = LoggerFactory.getLogger(GraphService.class);
    private final BargainDateRepository bargainDateRepository;
    private final CharterDateRepository charterDateRepository;
    private final RentDateRepository rentDateRepository;

    public GraphService(BargainDateRepository bargainDateRepository, CharterDateRepository charterDateRepository, RentDateRepository rentDateRepository) {
        this.bargainDateRepository = bargainDateRepository;
        this.charterDateRepository = charterDateRepository;
        this.rentDateRepository = rentDateRepository;
    }

    /*  그래프 디티오 조회 */
    public List<GraphDto> getGraphDtos(RegionDto regionDto, DateDto dateDto) {

        Map<String, List<GraphTmpDto>> graphTmpDtoMap = getGraphTmpDtoMap(regionDto, dateDto); // 디비 조회 후 맵 형식 변환
        logger.info("---------------------------1");
        graphTmpDtoMap = setDealTypeOnGraphTmpDtoList(graphTmpDtoMap);  // 그래프 템프 디티오에 딜타입 지정
        logger.info("---------------------------2");
        List<GraphDto> graphDtoList = mergeGraphTmpDtoListToGraphDtoList(graphTmpDtoMap, dateDto);  // 실제 반환 할 디티오로 변환
        logger.info("---------------------------3");
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

        List<GraphTmpDto> bargainDateGraphTmpDtos = bargainDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);
        List<GraphTmpDto> charterDateGraphTmpDtos = charterDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);
        List<GraphTmpDto> rentDateGraphTmpDtos = rentDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);

        graphTmpDtoMap.put("bargain", bargainDateGraphTmpDtos);
        graphTmpDtoMap.put("charter", charterDateGraphTmpDtos);
        graphTmpDtoMap.put("rent", rentDateGraphTmpDtos);

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

        String dealType = key;

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            graphTmpDto.setDealType(dealType);
        }

        return graphTmpDtoList;
    }

    /* 그래프 템프 디티오를 그래프 디티오로 변경 후 하나의 리스트로 합침 */
    public List<GraphDto> mergeGraphTmpDtoListToGraphDtoList(Map<String, List<GraphTmpDto>> graphTmpDtoMap, DateDto dateDto) {

        List<GraphDto> graphDtoList = new ArrayList<>();

        Set<String> keySet = graphTmpDtoMap.keySet();

        for (String s : keySet) {
            logger.info(s);
            graphDtoList.addAll(convertGraphTmpDtoListToGraphDtoList(graphTmpDtoMap.get(s), s, dateDto));
        }

        return graphDtoList;
    }

    /* 그래프 템프 디티오를 그래프 디티오로 변경 */
    public List<GraphDto> convertGraphTmpDtoListToGraphDtoList(List<GraphTmpDto> graphTmpDtoList, String deal, DateDto dateDto) {
        logger.info("convertGraphTmpDtoListToGraphDtoList");
        if(graphTmpDtoList.size() <= 0){
            return emptyGraphDtoList(deal);
        }

        Map<String, List<GraphTmpDto>> separateHousingTypeMap = separateHousingType(graphTmpDtoList);

        Set<String> keySet = separateHousingTypeMap.keySet();

        List<GraphDto> graphDtoList = new ArrayList<>();
        for (String s : keySet) {
            logger.info("convertGraphTmpDtoListToGraphDtoList in for ");
            GraphDto graphDto = null;
            if(separateHousingTypeMap.get(s).size() <= 0 ){
                graphDtoList.add(GraphDto.builder().dealType(deal).housingType(s).average(null).build());
                continue;
            }
            switch (dateDto.getDateType()) {
                case YEAR:
                    graphDto = setAverageListYear(separateHousingTypeMap.get(s));
                    break;
                case MONTH:
                    graphDto = setAverageListMonth(separateHousingTypeMap.get(s));
                    break;
                case DAY:
                    graphDto = setAverageListDay(separateHousingTypeMap.get(s));
                    break;
            }
            graphDtoList.add(graphDto);
        }

        return graphDtoList;
    }

    public List<GraphDto> emptyGraphDtoList(String dealType){

        logger.info("emptyGraphDtoList-------");

        List<GraphDto> emptyGraphDtoList = new ArrayList<>();

        List<String> housingTypes = Arrays.asList("apart", "officetel", "house");

        for (String housingType : housingTypes) {
            emptyGraphDtoList.add(GraphDto.builder().dealType(dealType).housingType(housingType).average(null).build());
        }

        return emptyGraphDtoList;
    }

    public Map<String, List<GraphTmpDto>> separateHousingType(List<GraphTmpDto> graphTmpDtoList){

        logger.info("separateHousingType-------");

        Map<String, List<GraphTmpDto>> separateHousingTypeMap = new HashMap<>();

        List<String> housingTypes = Arrays.asList("apart", "officetel", "house");

        for (String housingType : housingTypes) {
            separateHousingTypeMap.put(housingType, new ArrayList<>());
        }

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            List<GraphTmpDto> arr = separateHousingTypeMap.get(graphTmpDto.getHousingType());
            arr.add(graphTmpDto);
            separateHousingTypeMap.put(graphTmpDto.getHousingType(), arr);
        }

        return separateHousingTypeMap;
    }

    public GraphDto setAverageListYear(List<GraphTmpDto> graphTmpDtoList) {

        logger.info("setAverageListYear-------");

        if(graphTmpDtoList.size() <= 0){
            return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                    average(null).build();
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(graphTmpDtoList.get(0).getDate());

        int firstYear = calendar.get(Calendar.YEAR);
        List<Double> averageList = new ArrayList<>();

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            Calendar calendar2 = new GregorianCalendar();
            calendar2.setTime(graphTmpDto.getDate());

             int currentYear = calendar.get(Calendar.YEAR);
             if(currentYear == firstYear) {
                 averageList.add(graphTmpDto.getAverage());
             } else {
                 averageList.add(null);
             }
             firstYear++;
        }

        return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                average(averageList).build();
    }

    public GraphDto setAverageListMonth(List<GraphTmpDto> graphTmpDtoList) {

        if(graphTmpDtoList.size() <= 0){
            return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                    average(null).build();
        }

        List<Double> averageList = new ArrayList<>();

        for ( int i = 0 ; i <= 12 ; i++){
            averageList.add(null);
        }

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            logger.info("setAverageListMonth in for");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(graphTmpDto.getDate());

            int currentMonth = calendar.get(Calendar.MONTH);
            logger.info("currentMonth ---- " + currentMonth);
            averageList.add(currentMonth, graphTmpDto.getAverage());
            logger.info("averageList ---- " + averageList.get(currentMonth));
        }

        return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                average(averageList).build();
    }

    public GraphDto setAverageListDay(List<GraphTmpDto> graphTmpDtoList) {

        if(graphTmpDtoList.size() <= 0){
            return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                    average(null).build();
        }

        List<Double> averageList = new ArrayList<>();

        for ( int i = 0 ; i <= 31 ; i++){
            averageList.add(null);
        }

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(graphTmpDto.getDate());

            int currentMonth = calendar.get(Calendar.DATE);
            averageList.add(currentMonth, graphTmpDto.getAverage());
        }

        return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                average(averageList).build();
    }

}