package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.entity.Forsale;
import kr.ac.skuniv.realestate.mapper.ForsaleMap;
import kr.ac.skuniv.realestate.repository.ForsaleRepository;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ConditionService {

    private final ForsaleRepository forsaleRepository;
    private final ExcelConverterUtill excelConverterUtill;
    private HashMap<String, Integer> regionCode;

    @Autowired
    public ConditionService(ForsaleRepository forsaleRepository, ExcelConverterUtill excelConverterUtill){
        this.forsaleRepository = forsaleRepository;
        this.excelConverterUtill = excelConverterUtill;
    }

    public void setRegionCode(HashMap<String, Integer> regionCode){
        this.regionCode = regionCode;
    }

    public int convertRegionToCode(String regionName){
        regionCode = excelConverterUtill.getRegionCode();
        return regionCode.get(regionName);
    }

    public HashMap<String, Integer> test(){
        return excelConverterUtill.getRegionCode();
    }

    public List<Forsale> getTest(int code){
        return forsaleRepository.getCode(code);
    }

    public List<GraphDto> convertEntit2Dto(int code){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new ForsaleMap());

        List<Forsale> forsaleList = forsaleRepository.getCode(code);
        List<GraphDto> graphDtos = modelMapper.map(forsaleList, new TypeToken<List<GraphDto>>(){}.getType());

        return graphDtos;
    }


}
