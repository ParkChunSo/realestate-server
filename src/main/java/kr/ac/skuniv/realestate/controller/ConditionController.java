package kr.ac.skuniv.realestate.controller;

import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.domain.entity.Building;
import kr.ac.skuniv.realestate.service.ConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "realestate/condition/*")
public class ConditionController {
    Logger logger = LoggerFactory.getLogger(ConditionController.class);
    private final ConditionService conditionService;

    public ConditionController(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    @GetMapping("/city/{city}/date")
    public List<GraphDto> onlyCity(@PathVariable String city){
        RegionDto regionDto = conditionService.convertRegionToDto(city);
        return conditionService.getGraphDtoByRegionDto(regionDto);
    }

    @GetMapping("/city/{city}/date/{date}")
    public List<GraphDto> cityAndDate(@PathVariable String city, @PathVariable String date){
        RegionDto regionDto = conditionService.convertRegionToDto(city);
        return conditionService.getGraphDtoByRegionDtoAndDate(regionDto, date);
    }

    @GetMapping("/city/{city}/district/{district}/date")
    public List<GraphDto> cityAndDistrict(@PathVariable String city, @PathVariable String district){
        RegionDto regionDto = conditionService.convertRegionToDto(city, district);
        return conditionService.getGraphDtoByRegionDto(regionDto);
    }

    @GetMapping("/city/{city}/district/{district}/date/{date}")
    public List<GraphDto> cityAndDistrictAndDate(@PathVariable String city, @PathVariable String district, @PathVariable String date){
        RegionDto regionDto = conditionService.convertRegionToDto(city, district);
        return conditionService.getGraphDtoByRegionDtoAndDate(regionDto, date);
    }

    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}/date")
    public List<GraphDto> cityAndDistrictAndNeighborhood(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood){
        RegionDto regionDto = conditionService.convertRegionToDto(city, district, neighborhood);
        return conditionService.getGraphDtoByRegionDto(regionDto);
    }

    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}/date/{date}")
    public List<GraphDto> cityAndDistrictAndNeighborhoodAndDate(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood, @PathVariable String date){
        RegionDto regionDto = conditionService.convertRegionToDto(city,district,neighborhood);
        return conditionService.getGraphDtoByRegionDtoAndDate(regionDto, date);
    }

    @GetMapping("/test")
    public Iterable<Building> testQueryDsl() {
        return conditionService.search(1L,"11");
    }




    /*@GetMapping("/city/{city}/date")
    public ConditionDto onlyCity(@PathVariable String city){
        String tsetCode = conditionService.convertRegionToCode(city);
        List<GraphDto> graphDtos = conditionService.findDataByCode(conditionService.convertRegionToCode(city));
        List<MapDto> mapDtos = conditionService.getMapDtoByRegionCity(conditionService.convertRegionCityToCode(city), "city");

        return new ConditionDto(mapDtos, graphDtos);
    }*/
}