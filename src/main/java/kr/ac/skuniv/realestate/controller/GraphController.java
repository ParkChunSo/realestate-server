package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.service.GraphService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/realestate/graph")
public class GraphController {

    private Logger logger = LoggerFactory.getLogger(GraphController.class);
    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @ApiOperation("날짜없이 대코드 조회")
    @GetMapping("/city/{city}")
    public List<GraphDto> onlyCity(@PathVariable String city) {
        RegionDto regionDto = graphService.convertRegionToDto(city);
        DateDto dateDto = DateDto.builder()
                .dateType(DateDto.DateType.YEAR)
                .build();

        return graphService.getGraphDtos(regionDto, dateDto);
    }

    @ApiOperation("날짜와 대코드 조회")
    @GetMapping("/city/{city}/date/{date}")
    public List<GraphDto> cityAndDate(@PathVariable String city, @PathVariable String date) {
        RegionDto regionDto = graphService.convertRegionToDto(city);
        DateDto dateDto = graphService.convertDateToDto(date);

        return graphService.getGraphDtos(regionDto, dateDto);
    }

    @ApiOperation("날짜없이 대코드 중코드 조회")
    @GetMapping("/city/{city}/district/{district}")
    public List<GraphDto> cityAndDistrict(@PathVariable String city, @PathVariable String district) {
        RegionDto regionDto = graphService.convertRegionToDto(city, district);
        DateDto dateDto = DateDto.builder()
                .dateType(DateDto.DateType.YEAR)
                .build();

        return graphService.getGraphDtos(regionDto, dateDto);
    }

    @ApiOperation("날짜와 대코드 중코드 조회")
    @GetMapping("/city/{city}/district/{district}/date/{date}")
    public List<GraphDto> cityAndDistrictAndDate(@PathVariable String city, @PathVariable String district, @PathVariable String date) {
        RegionDto regionDto = graphService.convertRegionToDto(city, district);
        DateDto dateDto = graphService.convertDateToDto(date);

        return graphService.getGraphDtos(regionDto, dateDto);
    }

    @ApiOperation("날짜없이 대코드 중코드 소코드 조회")
    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}")
    public List<GraphDto> cityAndDistrictAndNeighborhood(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood) {
        RegionDto regionDto = graphService.convertRegionToDto(city, district, neighborhood);
        DateDto dateDto = DateDto.builder()
                .dateType(DateDto.DateType.YEAR)
                .build();

        return graphService.getGraphDtos(regionDto, dateDto);
    }

    @ApiOperation("날짜와 대코드 중코드 소코드 조회")
    @GetMapping("/city/{city}/district/{district}/neighborhood/{neighborhood}/date/{date}")
    public List<GraphDto> cityAndDistrictAndNeighborhoodAndDate(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood, @PathVariable String date) {
        RegionDto regionDto = graphService.convertRegionToDto(city, district, neighborhood);
        DateDto dateDto = graphService.convertDateToDto(date);

        return graphService.getGraphDtos(regionDto, dateDto);
    }

    @ApiOperation("날짜없이 대코드 소코드 조회(세종 특별시 경우)")
    @GetMapping("/city/{city}/neighborhood/{neighborhood}")
    public List<GraphDto> cityAndNeighborhood(@PathVariable String city, @PathVariable String neighborhood) {
        RegionDto regionDto = graphService.convertRegionToDto(city, "", neighborhood);
        DateDto dateDto = DateDto.builder()
                .dateType(DateDto.DateType.YEAR)
                .build();

        return graphService.getGraphDtos(regionDto, dateDto);
    }

    @ApiOperation("날짜와 대코드 소코드 조회(세종 특별시 경우)")
    @GetMapping("/city/{city}/neighborhood/{neighborhood}/date/{date}")
    public List<GraphDto> cityAndNeighborhoodAndDate(@PathVariable String city, @PathVariable String neighborhood, @PathVariable String date) {
        RegionDto regionDto = graphService.convertRegionToDto(city, "", neighborhood);
        DateDto dateDto = graphService.convertDateToDto(date);

        return graphService.getGraphDtos(regionDto, dateDto);
    }

}