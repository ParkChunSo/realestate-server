package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.MapDto;
import kr.ac.skuniv.realestate.domain.dto.MapTmpDto;
import kr.ac.skuniv.realestate.domain.entity.Forsale;
import kr.ac.skuniv.realestate.mapper.ForsaleMap;
import kr.ac.skuniv.realestate.repository.ForsaleRepository;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConditionService {

    private final ForsaleRepository forsaleRepository;
    private final ExcelConverterUtill excelConverterUtill;
    private HashMap<String, Integer> regionCode;
    private Logger logger = LoggerFactory.getLogger(ConditionService.class);
    @Autowired
    public ConditionService(ForsaleRepository forsaleRepository, ExcelConverterUtill excelConverterUtill){
        this.forsaleRepository = forsaleRepository;
        this.excelConverterUtill = excelConverterUtill;
    }

    public int convertRegionToCode(String regionName){
        regionCode = excelConverterUtill.getRegionCodeMap();
        return regionCode.get(regionName);//지역코드 가져옴
    }

    public List<GraphDto> convertEntit2Dto(int code){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new ForsaleMap());

        List<Forsale> forsaleList = forsaleRepository.getCode(code);
        List<GraphDto> graphDtos = modelMapper.map(forsaleList, new TypeToken<List<GraphDto>>(){}.getType());
        logger.info(forsaleList.size()+"");
        return graphDtos;
    }

    public List<MapTmpDto> getMapDtoByCode(String regionName, String regionUnit){
        int regionCode = convertRegionToCode(regionName);
        regionCode = convertRegionCodeToDbCode(String.valueOf(regionCode),regionUnit);

        List<Object[]> objects = forsaleRepository.getMapDtoByCode(regionCode);
        return converEntity2MapDto(objects);
    }

    public int convertRegionCodeToDbCode(String regionCode, String regionUnit) {
        switch(regionUnit) {
            case "district":
                regionCode = regionCode.substring(0,2);
                break;
            case "neighborhood":
                regionCode = regionCode.substring(0,5);
                break;
        }
        return Integer.parseInt(regionCode);
    }

    public List<MapTmpDto> converEntity2MapDto(List<Object[]> resultList){
        return resultList.stream().map(mapTmpDto -> new MapTmpDto(
                (int)mapTmpDto[0], (int)mapTmpDto[1], (long)mapTmpDto[2]
        )).collect(Collectors.toList());
    }
}
