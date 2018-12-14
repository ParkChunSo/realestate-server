package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.MapDto;
import kr.ac.skuniv.realestate.exception.UserDefineException;
import kr.ac.skuniv.realestate.repository.ForsaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConditionService {

    private final ForsaleRepository forsaleRepository;
    private HashMap<String, String> regionCodeHashmap;

    public ConditionService(ForsaleRepository forsaleRepository) {
        this.forsaleRepository = forsaleRepository;
    }

  public void setRegionCodeHashmap(HashMap<String, String> regionCodeHashmap) {
        this.regionCodeHashmap = regionCodeHashmap;
    }

    public List<GraphDto> findDataByCode(String regionCode){
        List<GraphTmpDto> graphTmpDtos = forsaleRepository.getByCodeAndDateOnYear(Integer.parseInt(regionCode))
                .stream().map(graphTmpDto -> new GraphTmpDto(
                (String)graphTmpDto[0], (String)graphTmpDto[1],
                (Date)graphTmpDto[2],(Double) graphTmpDto[3]
        )).collect(Collectors.toList());

        return convertTmpDto2GraphDto(graphTmpDtos);
    }

    public List<GraphDto> findDataByCode(String regionCode, String date){
        List<GraphTmpDto> graphTmpDtos = findDataByCodeAndDate(Integer.parseInt(regionCode), date)
                .stream().map(graphTmpDto -> new GraphTmpDto(
                        (String)graphTmpDto[0], (String)graphTmpDto[1],
                        (Date)graphTmpDto[2],(Double) graphTmpDto[3]
                )).collect(Collectors.toList());

        return convertTmpDto2GraphDto(graphTmpDtos);
    }


    public String convertRegionCityToCode(String city){
        return regionCode.get(city);
    }

    public String convertRegionToCode(String city){
        return regionCodeHashmap.get(city).substring(0, 2);
    }

    public String convertRegionToCode(String city, String distict){
        return regionCodeHashmap.get(city + distict).substring(0,5);
    }

    public String convertRegionToCode(String city, String distict, String neighborhood){
        return regionCodeHashmap.get(city + distict + neighborhood);
    }

    private List<Object[]> findDataByCodeAndDate(int code, String _date){
        String[] tmp = _date.split("-");

        if(tmp.length == 1)
            return forsaleRepository.getByCodeAndDateOnMonth(code, LocalDate.of(Integer.parseInt(tmp[0]),1,1));

        else if(tmp.length == 2)
            return forsaleRepository.getByCodeAndDateOnDay(code, LocalDate.of(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]),1));

        return null;
    }

    private List<GraphDto> convertTmpDto2GraphDto(List<GraphTmpDto> dtos){
        if(dtos.size() == 0)
            return null;

        List<GraphDto> graphDtos = new ArrayList<>();
        String dealType = dtos.get(0).getDealType(), housingType = dtos.get(0).getHousingType();
        ArrayList<Double> arrayList = new ArrayList<>();

        for (GraphTmpDto dto : dtos) {
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
  
    public List<MapDto> getMapDtoByRegion(String regionName, String regionUnit) {
        List<MapDto> mapDtos;

        try {
            String regionCode = convertRegionCodeToDbCode(regionName, regionUnit);
            List<Object[]> objects = forsaleRepository.getMapDtoByRegion(regionCode);
            mapDtos = convertObjectToMapDto(objects);
        } catch (Exception e) {
            throw new UserDefineException("getMapDtoByRegion Error", e.getCause());
        }

        return mapDtos;
    }

    public List<MapDto> getMapDtoByRegionCity(String regionName, String regionUnit) {
        List<MapDto> mapDtos;

        try {
            String regionCode = convertRegionCodeToDbCode(regionName, regionUnit);
            List<Object[]> objects = forsaleRepository.getMapDtoByRegionCity(regionCode);
            mapDtos = convertObjectToMapDto(objects);
        } catch (Exception e) {
            throw new UserDefineException("getMapDtoByRegionCity Error", e.getCause());
        }

        return mapDtos;
    }

    private String convertRegionCodeToDbCode(String regionCode, String regionUnit) {
        switch (regionUnit) {
            case "city":
                regionCode = regionCode.substring(2);
                break;
            case "district":
                regionCode = regionCode.substring(0, 2);
                break;
            case "neighborhood":
                regionCode = regionCode.substring(0, 5);
                break;
        }
        return regionCode;
    }

    private List<MapDto> convertObjectToMapDto(List<Object[]> resultList) {
        return resultList.stream().map(mapDto -> new MapDto(
                (String) mapDto[0], (int) mapDto[1], (long) mapDto[2]
        )).collect(Collectors.toList());
    }

}
