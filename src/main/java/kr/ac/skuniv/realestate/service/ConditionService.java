package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.domain.entity.RegionCode;
import kr.ac.skuniv.realestate.exception.UserDefineException;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.repository.CharterDateRepository;
import kr.ac.skuniv.realestate.repository.RegionCodeRepository;
import kr.ac.skuniv.realestate.repository.RentDateRepository;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConditionService {
    Logger logger = LoggerFactory.getLogger(ConditionService.class);
    private final BargainDateRepository bargainDateRepository;
    private final CharterDateRepository charterDateRepository;
    private final RentDateRepository rentDateRepository;
    private final RegionCodeRepository regionCodeRepository;
    private HashMap<String, String> regionCodeHashmap;

    public ConditionService(BargainDateRepository bargainDateRepository, CharterDateRepository charterDateRepository,
                            RentDateRepository rentDateRepository, RegionCodeRepository regionCodeRepository) {
        this.bargainDateRepository = bargainDateRepository;
        this.charterDateRepository = charterDateRepository;
        this.rentDateRepository = rentDateRepository;
        this.regionCodeRepository = regionCodeRepository;
    }

    public void setRegionCodeHashmap(HashMap<String, String> regionCodeHashmap) {
        this.regionCodeHashmap = regionCodeHashmap;
    }

//    public RegionDto convertRegionToDto(String city) {
//        if (regionCodeHashmap.get(city) == null) {
//            throw new UserDefineException("찾을수 없는 URL 파라미터");
//        } else {
//            return new RegionDto(regionCodeHashmap.get(city).substring(0, 2), "city");
//        }
//    }

    public RegionDto convertRegionToDto(String city) {
        RegionCode regionCode = regionCodeRepository.findById(city).get();
        if (regionCode.getValue() == null) {
            throw new UserDefineException("찾을수 없는 URL 파라미터");
        } else {
            return new RegionDto(regionCode.getValue().substring(0, 2), "city");
        }
    }

    public RegionDto convertRegionToDto(String city, String distict) {
        RegionCode regionCode = regionCodeRepository.findById(city + distict).get();
        if (regionCode.getValue() == null) {
            throw new UserDefineException("찾을수 없는 URL 파라미터");
        } else {
            return new RegionDto(regionCode.getValue().substring(0, 2), regionCode.getValue().substring(2, 5), "distict");
        }
    }

    public RegionDto convertRegionToDto(String city, String distict, String neighborhood) {
        RegionCode regionCode = regionCodeRepository.findById(city+distict+neighborhood).get();
        if (regionCode.getValue() == null) {
            throw new UserDefineException("찾을수 없는 URL 파라미터");
        } else {
            return new RegionDto(regionCode.getValue().substring(0, 2), regionCode.getValue().substring(2, 5), neighborhood, "neighborhood");
        }
    }

    public List<GraphDto> getGraphDtoByRegionDto(RegionDto regionDto) {
        List<Object[]> bargainDateObjects = new ArrayList<>();
        List<Object[]> charterDateObjects = new ArrayList<>();
        List<Object[]> rentDateObjects = new ArrayList<>();

        switch (regionDto.getRegionStatus()) {
            case "city":
                bargainDateObjects = bargainDateRepository.getByCityCodeAndDateOnYear(regionDto.getCityCode());

                charterDateObjects = charterDateRepository.getByCityCodeAndDateOnYear(regionDto.getCityCode());
                rentDateObjects = rentDateRepository.getByCityCodeAndDateOnYear(regionDto.getCityCode());
                break;
            case "district":
                bargainDateObjects = bargainDateRepository.getByGroopCodeAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode());
                charterDateObjects = charterDateRepository.getByGroopCodeAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode());
                rentDateObjects = rentDateRepository.getByGroopCodeAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode());
                break;
            case "neighborhood":
                bargainDateObjects = bargainDateRepository.getByDongNameAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName());
                charterDateObjects = charterDateRepository.getByDongNameAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName());
                rentDateObjects = rentDateRepository.getByDongNameAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName());
                break;
        }
        return mergeObjectsToGraphDtos(bargainDateObjects, charterDateObjects, rentDateObjects);
    }

    public List<GraphDto> getGraphDtoByRegionDtoAndDate(RegionDto regionDto, String date) {
        List<Object[]> bargainDateObjects = new ArrayList<>();
        List<Object[]> charterDateObjects = new ArrayList<>();
        List<Object[]> rentDateObjects = new ArrayList<>();
        String[] tmp = date.split("-");

        if (tmp.length == 1) {
            switch (regionDto.getRegionStatus()) {
                case "city":
                    bargainDateObjects = bargainDateRepository.getByCityCodeAndDateOnMonth(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    charterDateObjects = charterDateRepository.getByCityCodeAndDateOnMonth(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    rentDateObjects = rentDateRepository.getByCityCodeAndDateOnMonth(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    break;
                case "district":
                    bargainDateObjects = bargainDateRepository.getByGroopCodeAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    charterDateObjects = charterDateRepository.getByGroopCodeAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    rentDateObjects = rentDateRepository.getByGroopCodeAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    break;
                case "neighborhood":
                    bargainDateObjects = bargainDateRepository.getByDongNameAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    charterDateObjects = charterDateRepository.getByDongNameAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    rentDateObjects = rentDateRepository.getByDongNameAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    break;
            }
        } else if (tmp.length == 2) {
            switch (regionDto.getRegionStatus()) {
                case "city":
                    bargainDateObjects = bargainDateRepository.getByCityCodeAndDateOnDay(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    charterDateObjects = charterDateRepository.getByCityCodeAndDateOnDay(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    rentDateObjects = rentDateRepository.getByCityCodeAndDateOnDay(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    break;
                case "district":
                    bargainDateObjects = bargainDateRepository.getByGroopCodeAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    charterDateObjects = charterDateRepository.getByGroopCodeAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    rentDateObjects = rentDateRepository.getByGroopCodeAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    break;
                case "neighborhood":
                    bargainDateObjects = bargainDateRepository.getByDongNameAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    charterDateObjects = charterDateRepository.getByDongNameAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    rentDateObjects = rentDateRepository.getByDongNameAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    break;
            }
        }

        return mergeObjectsToGraphDtos(bargainDateObjects, charterDateObjects, rentDateObjects);
    }

    private List<GraphDto> mergeObjectsToGraphDtos(List<Object[]> bargainDateObjects, List<Object[]> charterDateObjects, List<Object[]> rentDateObjects) {
        List<GraphDto> bargainDateGraphDtos = new ArrayList<>();
        List<GraphDto> charterDateGraphDtos = new ArrayList<>();
        List<GraphDto> rentDateGraphDtos = new ArrayList<>();
        List<GraphDto> graphDtos;

        try {
            bargainDateGraphDtos = bargainDateObjects.size() > 0 ? convertObjectsToGraphDtos(bargainDateObjects, "매매") : bargainDateGraphDtos;
            charterDateGraphDtos = charterDateObjects.size() > 0 ? convertObjectsToGraphDtos(charterDateObjects, "전세") : charterDateGraphDtos;
            rentDateGraphDtos = rentDateObjects.size() > 0 ? convertObjectsToGraphDtos(rentDateObjects, "월세") : rentDateGraphDtos;

            graphDtos = ListUtils.union(bargainDateGraphDtos, charterDateGraphDtos);
            graphDtos = ListUtils.union(graphDtos, rentDateGraphDtos);
        } catch (Exception e) {
            throw new UserDefineException("GraphDto 병합 과정에서 오류", e.getMessage());
        }
        return graphDtos;
    }


    private List<GraphDto> convertObjectsToGraphDtos(List<Object[]> objects, String dealType) {
        List<GraphTmpDto> graphTmpDtos;
        try {
            graphTmpDtos = objects.stream().map(graphTmpDto -> new GraphTmpDto(dealType, (String) graphTmpDto[0],
                    (Date) graphTmpDto[1], (Double) graphTmpDto[2])).collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("convertObjectsToGraphDtos 예외 : " + e.getMessage());
            throw new UserDefineException("Objects -> GraphDto 변환과정에서 오류", e.getMessage());
        }
        return convertGraphTmpDtosToGraphDtos(graphTmpDtos);
    }

    // 쿼리 결과로 나온 오브젝트 리스트에서 평균을 리스트로 가지는 객체로 변환
    private List<GraphDto> convertGraphTmpDtosToGraphDtos(List<GraphTmpDto> graphTmpDtos) {
        List<GraphDto> graphDtos = new ArrayList<>();
        String dealType = graphTmpDtos.get(0).getDealType(), housingType = graphTmpDtos.get(0).getHousingType();
        ArrayList<Double> arrayList = new ArrayList<>();

        for (GraphTmpDto dto : graphTmpDtos) {
            if (dealType.equals(dto.getDealType()) && housingType.equals(dto.getHousingType())) {
                arrayList.add(dto.getAverage());
            } else {
                GraphDto graphDto = new GraphDto();
                graphDto.setDealType(dealType);
                graphDto.setHousingType(housingType);
                graphDto.setAverage(arrayList);
                graphDtos.add(graphDto);
                arrayList = new ArrayList<>();
                dealType = dto.getDealType();
                housingType = dto.getHousingType();
                arrayList.add(dto.getAverage());
            }
        }

        GraphDto graphDto = new GraphDto();
        graphDto.setDealType(dealType);
        graphDto.setHousingType(housingType);
        graphDto.setAverage(arrayList);
        graphDtos.add(graphDto);

        return graphDtos;
    }














































    /*public List<MapDto> getMapDtoByRegion(String regionName, String regionUnit) {
        List<MapDto> mapDtos;

        try {
            String regionCode = convertRegionCodeToDbCode(regionName, regionUnit);
            List<Object[]> objects = forsaleRepository.getMapDtoByRegion(regionCode);
            mapDtos = convertObjectToMapDto(objects);
        } catch (Exception e) {
            throw new UserDefineException("MapDto DB오류입니다", e.getCause());
        }
        return mapDtos.size() > 0 ? mapDtos : null;
    }

    public List<MapDto> getMapDtoByRegionCity(String regionName, String regionUnit) {
        List<MapDto> mapDtos;

        try {
            String regionCode = convertRegionCodeToDbCode(regionName, regionUnit);
            List<Object[]> objects = forsaleRepository.getMapDtoByRegionCity(regionCode);
            mapDtos = convertObjectToMapDto(objects);
        } catch (Exception e) {
            throw new UserDefineException("MapDto DB오류입니다", e.getCause());
        }
        return mapDtos.size() > 0 ? mapDtos : null;
    }

    private String convertRegionCodeToDbCode(String regionCode, String regionUnit) {
        switch (regionUnit) {
            case "city":
                regionCode = regionCode.substring(2);
                break;
            case "district":
                regionCode = regionCode.substring(0, 2);
                break;
            case "dongName":
                regionCode = regionCode.substring(0, 5);
                break;
        }
        return regionCode;
    }

    private List<MapDto> convertObjectToMapDto(List<Object[]> resultList) {
        List<MapDto> mapDtos;

        try {
            mapDtos = resultList.stream().map(mapDto -> new MapDto((String) mapDto[0], (int) mapDto[1], (long) mapDto[2])).collect(Collectors.toList());
        } catch (Exception e) {
            throw new UserDefineException("MapDto 변환 오류입니다", e.getCause());
        }
        return mapDtos;
    }
*/

}
