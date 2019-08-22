package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.repository.CharterDateRepository;
import kr.ac.skuniv.realestate.repository.RentDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
//@RequiredArgsConstructor
public class GraphService {
    private Logger logger = LoggerFactory.getLogger(GraphService.class);
    private final BargainDateRepository bargainDateRepository;
    private final CharterDateRepository charterDateRepository;
    private final RentDateRepository rentDateRepository;
    private static final List<String> HOUSING_TYPE = Arrays.asList("apart", "officetel", "house");

    public GraphService(BargainDateRepository bargainDateRepository, CharterDateRepository charterDateRepository, RentDateRepository rentDateRepository) {
        this.bargainDateRepository = bargainDateRepository;
        this.charterDateRepository = charterDateRepository;
        this.rentDateRepository = rentDateRepository;
    }

    /*  그래프 디티오 조회 */
    public List<GraphDto> getGraphDtos(RegionDto regionDto, DateDto dateDto) {

        Map<String, List<GraphTmpDto>> graphTmpDtoMap = getGraphTmpDtoMap(regionDto, dateDto); // 디비 조회 후 맵 형식 변환

        graphTmpDtoMap = setDealTypeOnGraphTmpDtoList(graphTmpDtoMap);  // 그래프 템프 디티오에 딜타입 지정

        List<GraphDto> graphDtoList = mergeGraphTmpDtoListToGraphDtoList(graphTmpDtoMap, dateDto);  // 실제 반환 할 디티오로 변환

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
    private Map<String, List<GraphTmpDto>> getGraphTmpDtoMap(RegionDto regionDto, DateDto dateDto) {
        Map<String, List<GraphTmpDto>> graphTmpDtoMap = new HashMap<>();

        List<GraphTmpDto> bargainDateGraphTmpDtos = bargainDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);
        List<GraphTmpDto> charterDateGraphTmpDtos = charterDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);
        List<GraphTmpDto> rentDateGraphTmpDtos = rentDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);

        graphTmpDtoMap.put("bargain", bargainDateGraphTmpDtos);
        graphTmpDtoMap.put("charter", charterDateGraphTmpDtos);
        graphTmpDtoMap.put("rent", rentDateGraphTmpDtos);

        return graphTmpDtoMap;
    }

    /* 그래프 템프 디티오 맵에 거래 타입 지정 */
    private Map<String, List<GraphTmpDto>> setDealTypeOnGraphTmpDtoList(Map<String, List<GraphTmpDto>> graphTmpDtoListMap) {

        Set<String> keySet = graphTmpDtoListMap.keySet();

        keySet.forEach(key -> {
            graphTmpDtoListMap.put(key, setDealType(graphTmpDtoListMap.get(key), key)); // 거래 타입 지정 후 저장
        });

        return graphTmpDtoListMap; // 거래 타입 지정 된 맵 반환
    }

    /* 그래프 템프 디티오에 거래 타입 지정 */
    private List<GraphTmpDto> setDealType(List<GraphTmpDto> graphTmpDtoList, String key) {

        String dealType = key;

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            graphTmpDto.setDealType(dealType);  // 그래프 템프 디티오에 거래 타입 지정
        }

        return graphTmpDtoList;
    }

    /* 그래프 템프 디티오를 그래프 디티오로 변경 후 하나의 리스트로 합침 */
    private List<GraphDto> mergeGraphTmpDtoListToGraphDtoList(Map<String, List<GraphTmpDto>> graphTmpDtoMap, DateDto dateDto) {

        List<GraphDto> graphDtoList = new ArrayList<>();

        Set<String> keySet = graphTmpDtoMap.keySet();

        for (String s : keySet) {
            graphDtoList.addAll(convertGraphTmpDtoListToGraphDtoList(graphTmpDtoMap.get(s), s, dateDto)); // 그래프 템프 -> 그래프 디티오로 변환 후 저장
        }

        return graphDtoList;
    }

    /* 그래프 템프 디티오를 그래프 디티오로 변경 */
    public List<GraphDto> convertGraphTmpDtoListToGraphDtoList(List<GraphTmpDto> graphTmpDtoList, String deal, DateDto dateDto) {
        logger.info("convertGraphTmpDtoListToGraphDtoList");
        if(graphTmpDtoList.size() <= 0){
            return emptyGraphDtoList(deal);
        }

        Map<String, List<GraphTmpDto>> separateHousingTypeMap = separateHousingType(graphTmpDtoList); // 하우징 타입 별로 구분

        Set<String> keySet = separateHousingTypeMap.keySet();

        List<GraphDto> graphDtoList = new ArrayList<>();

        for (String s : keySet) {
            graphDtoList.add(addGraphDto(separateHousingTypeMap.get(s), deal, s, dateDto)); // 하우징 타입 별로 구분 후 날짜 형식에 따라 디티오 변환 후 추가
        }

        return graphDtoList;
    }

    /* 빈 그래프 디티오 가져오기 */
    public List<GraphDto> emptyGraphDtoList(String dealType){
        List<GraphDto> emptyGraphDtoList = new ArrayList<>();

        for (String housingType : HOUSING_TYPE) {
            emptyGraphDtoList.add(GraphDto.builder().dealType(dealType).housingType(housingType).average(null).build());
        }

        return emptyGraphDtoList;
    }

    /* 하우징 타입 별로 구분 후 맵 으로 반환 */
    public Map<String, List<GraphTmpDto>> separateHousingType(List<GraphTmpDto> graphTmpDtoList){

        Map<String, List<GraphTmpDto>> separateHousingTypeMap = new HashMap<>();

        for (String housingType : HOUSING_TYPE) {
            separateHousingTypeMap.put(housingType, new ArrayList<>());
        }

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            List<GraphTmpDto> arr = separateHousingTypeMap.get(graphTmpDto.getHousingType());
            arr.add(graphTmpDto);
            separateHousingTypeMap.put(graphTmpDto.getHousingType(), arr);
        }
        return separateHousingTypeMap;
    }

    /* 연도 별 일 경우에 평균 값 리스트 반환 */
    public GraphDto setAverageListYear(List<GraphTmpDto> graphTmpDtoList) {
        int prevYear = getDate(graphTmpDtoList.get(0), Calendar.YEAR);
        List<Double> averageList = new ArrayList<>();

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            int currentYear = getDate(graphTmpDto, Calendar.YEAR);
            if(currentYear == prevYear) {
                averageList.add(graphTmpDto.getAverage());
            } else {
                averageList.add(null);
            }
            prevYear++;
        }

        return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                average(averageList).build();
    }

    /* 월 별 일 경우에 평균 값 리스트 반환 */
    public GraphDto setAverageListMonth(List<GraphTmpDto> graphTmpDtoList) {
        List<Double> averageList = initAverageList(12);

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            int currentMonth = getDate(graphTmpDto, Calendar.MONTH);
            averageList.add(currentMonth, graphTmpDto.getAverage());
        }

        return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                average(averageList).build();
    }

    /* 일 별 일 경우에 평균 값 리스트 반환 */
    public GraphDto setAverageListDay(List<GraphTmpDto> graphTmpDtoList) {
        List<Double> averageList = initAverageList(31);

        for (GraphTmpDto graphTmpDto : graphTmpDtoList) {
            int currentMonth = getDate(graphTmpDto, Calendar.DATE);
            averageList.add(currentMonth, graphTmpDto.getAverage());
        }

        return GraphDto.builder().housingType(graphTmpDtoList.get(0).getHousingType()).dealType(graphTmpDtoList.get(0).getDealType()).
                average(averageList).build();
    }

    /* 날짜 가져오기 */
    private int getDate(GraphTmpDto graphTmpDto, int type) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(graphTmpDto.getDate());

        return calendar.get(type);
    }

    /* 평균 리스트 초기화 */
    private List<Double> initAverageList(int size) {
        List<Double> averageList = new ArrayList<>();

        for ( int i = 0 ; i <= size ; i++){
            averageList.add(null);
        }

        return averageList;
    }

    /* 그래프 디티오 변환 후 반환 */
    private GraphDto addGraphDto(List<GraphTmpDto> graphTmpDtoList, String deal, String housing,DateDto dateDto) {
        GraphDto graphDto = null;
        if(graphTmpDtoList.size() <= 0 ){
            //graphDtoList.add(GraphDto.builder().dealType(deal).housingType(s).average(null).build());
            return GraphDto.builder().dealType(deal).housingType(housing).average(null).build();
        }
        switch (dateDto.getDateType()) {
            case YEAR:
                graphDto = setAverageListYear(graphTmpDtoList);
                break;
            case MONTH:
                graphDto = setAverageListMonth(graphTmpDtoList);
                break;
            case DAY:
                graphDto = setAverageListDay(graphTmpDtoList);
                break;
        }
        return graphDto;
    }
}