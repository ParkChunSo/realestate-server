package kr.ac.skuniv.realestate.controller;

import kr.ac.skuniv.realestate.domain.dto.ConditionDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.MapDto;
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
    private final ConditionService conditionService;
    Logger logger = LoggerFactory.getLogger(ConditionController.class);

    public ConditionController(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    @GetMapping("/city/{city}/date")
    public ConditionDto onlyCity(@PathVariable String city){
        String regionName = conditionService.convertRegionCityToCode(city);
        List<MapDto> mapDtos = conditionService.getMapDtoByRegionCity(regionName, "city");

        List<GraphDto> graphDtos = conditionService.convertTmpDto2GraphDto(
                conditionService.convertEntity2Dto(
                        conditionService.getByCodeAndDateOnYear(
                                conditionService.convertRegionToCode(city)
                        )
                )
        );
        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/date/{date}")
    public ConditionDto cityAndDate(@PathVariable String city, @PathVariable String date){
        String regionName = conditionService.convertRegionToCode(city);
        List<MapDto> mapDtos = conditionService.getMapDtoByRegionCity(regionName, "city");

        List<GraphDto> graphDtos = conditionService.convertTmpDto2GraphDto(
                conditionService.convertEntity2Dto(
                        conditionService.getByCodeAndDateOnMonth(
                                conditionService.convertRegionToCode(city), conditionService.convertString2LocalDate(date)
                        )
                )
        );
        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/district/{district}/date")
    public ConditionDto cityAndDistrict(@PathVariable String city, @PathVariable String district){
        String regionName = conditionService.convertRegionToCode(city, district);
        List<MapDto> mapDtos = conditionService.getMapDtoByRegion(regionName, "neighborhood");

        List<GraphDto> graphDtos = conditionService.convertTmpDto2GraphDto(
                conditionService.convertEntity2Dto(
                        conditionService.getByCodeAndDateOnYear(
                                conditionService.convertRegionToCode(city, district)
                        )
                )
        );
        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/district/{district}/date/{date}")
    public ConditionDto cityAndDistrictAndDate(@PathVariable String city, @PathVariable String district, @PathVariable String date){
        String regionName = conditionService.convertRegionToCode(city, district);
        List<MapDto> mapDtos = conditionService.getMapDtoByRegion(regionName, "district");

        List<GraphDto> graphDtos = conditionService.convertTmpDto2GraphDto(
                conditionService.convertEntity2Dto(
                        conditionService.getByCodeAndDateOnMonth(
                                conditionService.convertRegionToCode(city, district), conditionService.convertString2LocalDate(date)
                        )
                )
        );
        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}/date")
    public ConditionDto cityAndDistrictAndNeighborhood(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood){
        String regionName = conditionService.convertRegionToCode(city, district, neighborhood);
        List<MapDto> mapDtos = conditionService.getMapDtoByRegion(regionName, "neighborhood");

        List<GraphDto> graphDtos = conditionService.convertTmpDto2GraphDto(
                conditionService.convertEntity2Dto(
                        conditionService.getByCodeAndDateOnYear(
                                conditionService.convertRegionToCode(city, district, neighborhood)
                        )
                )
        );
        return new ConditionDto(mapDtos, graphDtos);
    }

    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}/date/{date}")
    public ConditionDto cityAndDistrictAndNeighborhoodAndDate(@PathVariable String city, @PathVariable String district,@PathVariable String neighborhood, @PathVariable String date){
        String regionName = conditionService.convertRegionToCode(city, district, neighborhood);
        List<MapDto> mapDtos = conditionService.getMapDtoByRegion(regionName, "neighborhood");

        List<GraphDto> graphDtos = conditionService.convertTmpDto2GraphDto(
                conditionService.convertEntity2Dto(
                        conditionService.getByCodeAndDateOnMonth(
                                conditionService.convertRegionToCode(city, district, neighborhood), conditionService.convertString2LocalDate(date)
                        )
                )
        );
        return new ConditionDto(mapDtos, graphDtos);
    }

//    @GetMapping("/test/city/{city}/district/{district}/neighborhood/{neighborhood}/date/{date}")
@GetMapping("/test/city/{city}/date/{date}")
    public List<GraphDto> test(@PathVariable String city, @PathVariable String date){
        List<GraphDto> graphDtos = conditionService.convertTmpDto2GraphDto(
                conditionService.convertEntity2Dto(
                        conditionService.getByCodeAndDateOnMonth(
                                conditionService.convertRegionToCode(city), conditionService.convertString2LocalDate(date)
                        )
                )
        );

        return graphDtos;

    }


    @GetMapping("/test1/city/{city}/district/{district}/neighborhood/{neighborhood}/date")
    public List<Object> test2(@PathVariable String city, @PathVariable String district,@PathVariable String neighborhood){
        List<Object> objects = null;//forsaleRepository.getByCodeAndDateOnYear(Integer.parseInt(conditionService.convertRegionToCode(city, district,neighborhood)));

        return objects;
    }
}