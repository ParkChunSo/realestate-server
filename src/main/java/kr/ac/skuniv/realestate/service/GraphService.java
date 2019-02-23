package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.*;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.repository.CharterDateRepository;
import kr.ac.skuniv.realestate.repository.RentDateRepository;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GraphService {
    private final BargainDateRepository bargainDateRepository;
    private final CharterDateRepository charterDateRepository;
    private final RentDateRepository rentDateRepository;
    private HashMap<String, String> regionCodeHashmap;

    public GraphService(BargainDateRepository bargainDateRepository, CharterDateRepository charterDateRepository, RentDateRepository rentDateRepository) {
        this.bargainDateRepository = bargainDateRepository;
        this.charterDateRepository = charterDateRepository;
        this.rentDateRepository = rentDateRepository;
    }

    public void setRegionCodeHashmap(HashMap<String, String> regionCodeHashmap) {
        this.regionCodeHashmap = regionCodeHashmap;
    }

    public List<GraphDto> getGraphDtos(RegionDto regionDto, DateDto dateDto) {
        return getGraphDtoByRegionDtoAndDateDto(regionDto, dateDto);
    }

    public RegionDto convertRegionToDto(String city) {
        return RegionDto.builder()
                .cityCode(regionCodeHashmap.get(city).substring(0, 2))
                .regionType(RegionDto.RegionType.CITY)
                .build();
    }

    public RegionDto convertRegionToDto(String city, String distict) {
        return RegionDto.builder()
                .cityCode(regionCodeHashmap.get(city + distict).substring(0, 2))
                .groopCode(regionCodeHashmap.get(city + distict).substring(2, 5))
                .regionType(RegionDto.RegionType.DISTRICT)
                .build();
    }

    public RegionDto convertRegionToDto(String city, String distict, String neighborhood) {
        return RegionDto.builder()
                .cityCode(regionCodeHashmap.get(city + distict).substring(0, 2))
                .groopCode(regionCodeHashmap.get(city + distict).substring(2, 5))
                .dongName(neighborhood)
                .regionType(RegionDto.RegionType.NEIGHBORHOOD)
                .build();
    }

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

    public List<GraphDto> getGraphDtoByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto) {
        List<GraphTmpDto> bargainDateGraphTmpDtos = new ArrayList<>();
        List<GraphTmpDto> charterDateGraphTmpDtos = new ArrayList<>();
        List<GraphTmpDto> rentDateGraphTmpDtos = new ArrayList<>();

        bargainDateGraphTmpDtos = bargainDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);
        charterDateGraphTmpDtos = charterDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);
        rentDateGraphTmpDtos = rentDateRepository.getByRegionDtoAndDateDto(regionDto, dateDto);

        return mergeGraphTmpDtosToGraphDtos(bargainDateGraphTmpDtos, charterDateGraphTmpDtos, rentDateGraphTmpDtos);
    }

    public List<GraphDto> mergeGraphTmpDtosToGraphDtos(List<GraphTmpDto> bargainDateGraphTmpDtos, List<GraphTmpDto> charterDateGraphTmpDtos, List<GraphTmpDto> rentDateGraphTmpDtos) {
        List<GraphDto> bargainDateGraphDtos = new ArrayList<>();
        List<GraphDto> charterDateGraphDtos = new ArrayList<>();
        List<GraphDto> rentDateGraphDtos = new ArrayList<>();
        List<GraphDto> graphDtos = new ArrayList<>();

        bargainDateGraphDtos = bargainDateGraphTmpDtos.size() > 0 ? setDealTypeOnGraphTmpDtos(bargainDateGraphTmpDtos, "매매") : bargainDateGraphDtos;
        charterDateGraphDtos = charterDateGraphTmpDtos.size() > 0 ? setDealTypeOnGraphTmpDtos(charterDateGraphTmpDtos, "전세") : charterDateGraphDtos;
        rentDateGraphDtos = rentDateGraphTmpDtos.size() > 0 ? setDealTypeOnGraphTmpDtos(rentDateGraphTmpDtos, "월세") : rentDateGraphDtos;

        graphDtos = ListUtils.union(bargainDateGraphDtos, charterDateGraphDtos);
        graphDtos = ListUtils.union(graphDtos, rentDateGraphDtos);
        return graphDtos;
    }

    public List<GraphDto> setDealTypeOnGraphTmpDtos(List<GraphTmpDto> graphTmpDtos, String dealType) {

        for (GraphTmpDto graphTmpDto : graphTmpDtos) {
            graphTmpDto.setDealType(dealType);
        }

        return convertGraphTmpDtosToGraphDtos(graphTmpDtos);
    }

    public List<GraphDto> convertGraphTmpDtosToGraphDtos(List<GraphTmpDto> graphTmpDtos) {
        List<GraphDto> graphDtos = new ArrayList<>();
        String dealType = graphTmpDtos.get(0).getDealType();
        String housingType = graphTmpDtos.get(0).getHousingType();
        ArrayList<Double> arrayList = new ArrayList<>();

        for (GraphTmpDto graphTmpDto : graphTmpDtos) {
            if (dealType.equals(graphTmpDto.getDealType()) && housingType.equals(graphTmpDto.getHousingType())) {
                arrayList.add(graphTmpDto.getAverage());
            } else {
                graphDtos.add(GraphDto.builder().dealType(dealType).housingType(housingType).average(arrayList).build());
                arrayList = new ArrayList<>();
                dealType = graphTmpDto.getDealType();
                housingType = graphTmpDto.getHousingType();
                arrayList.add(graphTmpDto.getAverage());
            }
        }

        graphDtos.add(GraphDto.builder().dealType(dealType).housingType(housingType).average(arrayList).build());
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
