package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.dto.ConditionDto;
import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
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

    @ApiOperation("날짜없이 대코드 조회")
    @GetMapping("/city/{city}")
    public ConditionDto onlyCity(@PathVariable String city) {
        RegionDto regionDto = conditionService.convertRegionToDto(city);
        DateDto dateDto = DateDto.builder()
                .dateType(DateDto.DateType.YEAR)
                .build();

        return conditionService.getConditionDto(regionDto, dateDto);
    }

    @ApiOperation("날짜와 대코드 조회")
    @GetMapping("/city/{city}/date/{date}")
    public ConditionDto cityAndDate(@PathVariable String city, @PathVariable String date) {
        RegionDto regionDto = conditionService.convertRegionToDto(city);
        DateDto dateDto = conditionService.convertDateToDto(date);

        return conditionService.getConditionDto(regionDto, dateDto);
    }

    @ApiOperation("날짜없이 대코드 중코드 조회")
    @GetMapping("/city/{city}/district/{district}")
    public ConditionDto cityAndDistrict(@PathVariable String city, @PathVariable String district) {
        RegionDto regionDto = conditionService.convertRegionToDto(city, district);
        DateDto dateDto = DateDto.builder()
                .dateType(DateDto.DateType.YEAR)
                .build();

        return conditionService.getConditionDto(regionDto, dateDto);
    }

    @ApiOperation("날짜와 대코드 중코드 조회")
    @GetMapping("/city/{city}/district/{district}/date/{date}")
    public ConditionDto cityAndDistrictAndDate(@PathVariable String city, @PathVariable String district, @PathVariable String date) {
        RegionDto regionDto = conditionService.convertRegionToDto(city, district);
        DateDto dateDto = conditionService.convertDateToDto(date);

        return conditionService.getConditionDto(regionDto, dateDto);
    }

    @ApiOperation("날짜없이 대코드 중코드 소코드 조회")
    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}")
    public ConditionDto cityAndDistrictAndNeighborhood(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood) {
        RegionDto regionDto = conditionService.convertRegionToDto(city, district, neighborhood);
        DateDto dateDto = DateDto.builder()
                .dateType(DateDto.DateType.YEAR)
                .build();

        return conditionService.getConditionDto(regionDto, dateDto);
    }

    @ApiOperation("날짜와 대코드 중코드 소코드 조회")
    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}/date/{date}")
    public ConditionDto cityAndDistrictAndNeighborhoodAndDate(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood, @PathVariable String date) {
        RegionDto regionDto = conditionService.convertRegionToDto(city, district, neighborhood);
        DateDto dateDto = conditionService.convertDateToDto(date);

        return conditionService.getConditionDto(regionDto, dateDto);
    }

}