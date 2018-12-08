package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.entity.Forsale;
import kr.ac.skuniv.realestate.repository.ForsaleRepository;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@Service
public class ConditionService {

    private final ForsaleRepository forsaleRepository;
    private final ExcelConverterUtill excelConverterUtill;
    private final String dealTypes[] = {"월세" , "전세" , "매매"};
    private final String housingTypes[] = {"아파트", "오피스텔", "주택"};
    private HashMap<String, Integer> regionCode;
    private final Logger logger = LogManager.getLogger(ConditionService.class);

    @Autowired
    public ConditionService(ForsaleRepository forsaleRepository, ExcelConverterUtill excelConverterUtill){
        this.forsaleRepository = forsaleRepository;
        this.excelConverterUtill = excelConverterUtill;
    }

    public int convertRegionToCode(String regionName){
        regionCode = excelConverterUtill.getRegionCodeMap();
        logger.info("region code = " + regionCode.get(regionName));
        return regionCode.get(regionName);
    }

    public List<Forsale> convertEntityDto(int code){
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.addMappings(new ForsaleMap());
        //List<Forsale> forsaleList = forsaleRepository.getCode(code);
        List<Forsale> forsaleList = forsaleRepository.findByCode(code);
//        List<GraphDto> graphDtos = modelMapper.map(forsaleList, new TypeToken<List<GraphDto>>(){}.getType());

        logger.info("forsaleList size = " + forsaleList.size());
        return forsaleList;
    }

    public List<Forsale> selectByCode(int code){
        List<Forsale> forsaleList = forsaleRepository.findByCode(code);

        logger.info("forsaleList size = " + forsaleList.size());
        return forsaleList;
    }

    public Object getAve(){
        //Object ave= forsaleRepository.getAve();
        //Object temp = new ArrayList<Integer>();
        Object temp = forsaleRepository.getAve();
        //List<Integer> result = cast(temp);
        //logger.info("forsaleList size = " + forsaleList.size());
       // for (int i = 0 ; i < ave.size(); i++)
         //   logger.info("==========" + (int)i);
        return temp;
    }
}