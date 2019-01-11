package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.domain.entity.Building;
import kr.ac.skuniv.realestate.predicate.BuildingPredicate;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.repository.BuildingRepository;
import kr.ac.skuniv.realestate.repository.CharterDateRepository;
import kr.ac.skuniv.realestate.repository.RentDateRepository;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConditionService {
    private Logger logger = LoggerFactory.getLogger(ConditionService.class);
    private final BargainDateRepository bargainDateRepository;
    private final CharterDateRepository charterDateRepository;
    private final RentDateRepository rentDateRepository;
    private HashMap<String, String> regionCodeHashmap;

    public ConditionService(BargainDateRepository bargainDateRepository, CharterDateRepository charterDateRepository, RentDateRepository rentDateRepository) {
        this.bargainDateRepository = bargainDateRepository;
        this.charterDateRepository = charterDateRepository;
        this.rentDateRepository = rentDateRepository;
    }

    public void setRegionCodeHashmap(HashMap<String, String> regionCodeHashmap) {
        this.regionCodeHashmap = regionCodeHashmap;
    }

    public RegionDto convertRegionToDto(String city) {
        return new RegionDto(regionCodeHashmap.get(city).substring(0, 2), RegionDto.RegionType.CITY);
    }

    public RegionDto convertRegionToDto(String city, String distict) {
        return new RegionDto(regionCodeHashmap.get(city + distict).substring(0, 2), regionCodeHashmap.get(city + distict).substring(2, 5), RegionDto.RegionType.DISTRICT);
    }

    public RegionDto convertRegionToDto(String city, String distict, String neighborhood) {
        return new RegionDto(regionCodeHashmap.get(city + distict).substring(0, 2), regionCodeHashmap.get(city + distict).substring(2, 5), neighborhood, RegionDto.RegionType.NEIGHBORHOOD);
    }

    @Transactional
    public List<GraphDto> getGraphDtoByRegionDto(RegionDto regionDto) {
        List<Object[]> bargainDateObjects = new ArrayList<>();
        List<Object[]> charterDateObjects = new ArrayList<>();
        List<Object[]> rentDateObjects = new ArrayList<>();

        switch (regionDto.getRegionType()) {
            case CITY:
                bargainDateObjects = bargainDateRepository.getByCityCodeAndDateOnYear(regionDto.getCityCode());
                charterDateObjects = charterDateRepository.getByCityCodeAndDateOnYear(regionDto.getCityCode());
                rentDateObjects = rentDateRepository.getByCityCodeAndDateOnYear(regionDto.getCityCode());
                break;
            case DISTRICT:
                bargainDateObjects = bargainDateRepository.getByGroopCodeAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode());
                charterDateObjects = charterDateRepository.getByGroopCodeAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode());
                rentDateObjects = rentDateRepository.getByGroopCodeAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode());
                break;
            case NEIGHBORHOOD:
                bargainDateObjects = bargainDateRepository.getByDongNameAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName());
                charterDateObjects = charterDateRepository.getByDongNameAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName());
                rentDateObjects = rentDateRepository.getByDongNameAndDateOnYear(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName());
                break;
        }
        return mergeObjectsToGraphDtos(bargainDateObjects, charterDateObjects, rentDateObjects);
    }

    @Transactional
    public List<GraphDto> getGraphDtoByRegionDtoAndDate(RegionDto regionDto, String date) {
        List<Object[]> bargainDateObjects = new ArrayList<>();
        List<Object[]> charterDateObjects = new ArrayList<>();
        List<Object[]> rentDateObjects = new ArrayList<>();
        String[] tmp = date.split("-");

        if (tmp.length == 1) {
            switch (regionDto.getRegionType()) {
                case CITY:
                    bargainDateObjects = bargainDateRepository.getByCityCodeAndDateOnMonth(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    charterDateObjects = charterDateRepository.getByCityCodeAndDateOnMonth(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    rentDateObjects = rentDateRepository.getByCityCodeAndDateOnMonth(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    break;
                case DISTRICT:
                    bargainDateObjects = bargainDateRepository.getByGroopCodeAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    charterDateObjects = charterDateRepository.getByGroopCodeAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    rentDateObjects = rentDateRepository.getByGroopCodeAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    break;
                case NEIGHBORHOOD:
                    bargainDateObjects = bargainDateRepository.getByDongNameAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    charterDateObjects = charterDateRepository.getByDongNameAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    rentDateObjects = rentDateRepository.getByDongNameAndDateOnMonth(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), 1, 1));
                    break;
            }
        } else if (tmp.length == 2) {
            switch (regionDto.getRegionType()) {
                case CITY:
                    bargainDateObjects = bargainDateRepository.getByCityCodeAndDateOnDay(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    charterDateObjects = charterDateRepository.getByCityCodeAndDateOnDay(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    rentDateObjects = rentDateRepository.getByCityCodeAndDateOnDay(regionDto.getCityCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    break;
                case DISTRICT:
                    bargainDateObjects = bargainDateRepository.getByGroopCodeAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    charterDateObjects = charterDateRepository.getByGroopCodeAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    rentDateObjects = rentDateRepository.getByGroopCodeAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    break;
                case NEIGHBORHOOD:
                    bargainDateObjects = bargainDateRepository.getByDongNameAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    charterDateObjects = charterDateRepository.getByDongNameAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    rentDateObjects = rentDateRepository.getByDongNameAndDateOnDay(regionDto.getCityCode(), regionDto.getGroopCode(), regionDto.getDongName(), LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1));
                    break;
            }
        }
        return mergeObjectsToGraphDtos(bargainDateObjects, charterDateObjects, rentDateObjects);
    }

    public List<GraphDto> mergeObjectsToGraphDtos(List<Object[]> bargainDateObjects, List<Object[]> charterDateObjects, List<Object[]> rentDateObjects) {
        List<GraphDto> bargainDateGraphDtos = new ArrayList<>();
        List<GraphDto> charterDateGraphDtos = new ArrayList<>();
        List<GraphDto> rentDateGraphDtos = new ArrayList<>();

        bargainDateGraphDtos = bargainDateObjects.size() > 0 ? convertObjectsToGraphDtos(bargainDateObjects, "매매") : bargainDateGraphDtos;
        charterDateGraphDtos = charterDateObjects.size() > 0 ? convertObjectsToGraphDtos(charterDateObjects, "전세") : charterDateGraphDtos;
        rentDateGraphDtos = rentDateObjects.size() > 0 ? convertObjectsToGraphDtos(rentDateObjects, "월세") : rentDateGraphDtos;

        List<GraphDto> graphDtos = ListUtils.union(bargainDateGraphDtos, charterDateGraphDtos);
        graphDtos = ListUtils.union(graphDtos, rentDateGraphDtos);
        return graphDtos;
    }

    public List<GraphDto> convertObjectsToGraphDtos(List<Object[]> objects, String dealType) {
        List<GraphTmpDto> graphTmpDtos = objects.stream().map(graphTmpDto -> new GraphTmpDto(dealType, (String) graphTmpDto[0],
                (Date) graphTmpDto[1], (Double) graphTmpDto[2])).collect(Collectors.toList());

        return convertGraphTmpDtosToGraphDtos(graphTmpDtos);
    }

    public List<GraphDto> convertGraphTmpDtosToGraphDtos(List<GraphTmpDto> graphTmpDtos) {
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

    @Autowired
    BuildingRepository buildingRepository;

    public Iterable<Building> search(Long builgingNo, String city) {
        return buildingRepository.findAll(BuildingPredicate.search(builgingNo, city));
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
