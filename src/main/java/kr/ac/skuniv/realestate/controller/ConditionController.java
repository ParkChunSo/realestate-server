package kr.ac.skuniv.realestate.controller;

import kr.ac.skuniv.realestate.domain.dto.ConditionDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.MapDto;
import kr.ac.skuniv.realestate.repository.BargainDateRepository;
import kr.ac.skuniv.realestate.service.ConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "realestate/condition/*")
public class ConditionController {
    private final ConditionService conditionService;
    Logger logger = LoggerFactory.getLogger(ConditionController.class);

    @Autowired
    BargainDateRepository bargainDateRepository;

    public ConditionController(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    @GetMapping("/city/{city}/date")
    public ConditionDto onlyCity(@PathVariable String city){
        List<GraphDto> graphDtos = conditionService.findDataByCode(conditionService.convertRegionToCode(city));
        List<MapDto> mapDtos = conditionService.getMapDtoByRegionCity(conditionService.convertRegionCityToCode(city), "city");

        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/date/{date}")
    public ConditionDto cityAndDate(@PathVariable String city, @PathVariable String date){
      List<GraphDto> graphDtos = conditionService.findDataByCode(conditionService.convertRegionToCode(city), date);
      List<MapDto> mapDtos = conditionService.getMapDtoByRegionCity(conditionService.convertRegionCityToCode(city), "city");

      return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/district/{district}/date")
    public ConditionDto cityAndDistrict(@PathVariable String city, @PathVariable String district){
        List<GraphDto> graphDtos = conditionService.findDataByCode(conditionService.convertRegionToCode(city, district));
        List<MapDto> mapDtos = conditionService.getMapDtoByRegion(conditionService.convertRegionToCode(city, district), "district");

        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/district/{district}/date/{date}")
    public ConditionDto cityAndDistrictAndDate(@PathVariable String city, @PathVariable String district, @PathVariable String date){
        List<GraphDto> graphDtos = conditionService.findDataByCode(conditionService.convertRegionToCode(city, district), date);
        List<MapDto> mapDtos = conditionService.getMapDtoByRegion(conditionService.convertRegionToCode(city, district), "district");

        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}/date")
    public ConditionDto cityAndDistrictAndNeighborhood(@PathVariable String city, @PathVariable String district,@PathVariable String neighborhood){
        List<GraphDto> graphDtos = conditionService.findDataByCode(conditionService.convertRegionToCode(city, district, neighborhood));
        List<MapDto> mapDtos = conditionService.getMapDtoByRegion(conditionService.convertRegionToCode(city, district, neighborhood), "neighborhood");

        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}/date/{date}")
    public ConditionDto cityAndDistrictAndNeighborhoodAndDate(@PathVariable String city, @PathVariable String district,@PathVariable String neighborhood, @PathVariable String date){
        List<GraphDto> graphDtos =conditionService.findDataByCode(conditionService.convertRegionToCode(city, district, neighborhood), date);
        List<MapDto> mapDtos = conditionService.getMapDtoByRegion(conditionService.convertRegionToCode(city, district, neighborhood), "neighborhood");

        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/test/city/{city}/date")
    public List<Object[]> test(@PathVariable String city){
        logger.info("=======================================================");
        String code = conditionService.convertRegionToCode(city);
        logger.info("=======================================================" + code);
        List<Object[]> graphTmpDtos = bargainDateRepository.getByCodeAndDateOnYear(code);
        return graphTmpDtos;
    }
}