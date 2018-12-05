package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.controller.ConditionController;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
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

import java.util.HashMap;
import java.util.List;



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
        return regionCode.get(regionName);
    }

    public List<GraphDto> convertEntit2Dto(int code){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new ForsaleMap());

        List<Forsale> forsaleList = forsaleRepository.getCode(code);
        List<GraphDto> graphDtos = modelMapper.map(forsaleList, new TypeToken<List<GraphDto>>(){}.getType());

        logger.info(forsaleList.size()+"");
        return graphDtos;
    }
}
