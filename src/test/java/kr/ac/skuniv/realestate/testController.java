package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by youngman on 2019-01-21.
 */
public class testController {

    @Autowired
    private GraphService graphService;

    /*@GetMapping("/getByRegionDtoAndDateDto")
    public Iterable<Building> testQueryDsl() {
        return conditionService.search(1L,"11");
    }*/

    @GetMapping("/test/city/{city}")
    public List<GraphDto> testCity(@PathVariable String city) {
        RegionDto regionDto = graphService.convertRegionToDto(city);
        return graphService.getGraphDtoByRegionDtoAndDateDto(regionDto, new DateDto(null, DateDto.DateType.YEAR));
    }

    @GetMapping("/test/city/{city}/district/{district}")
    public List<GraphDto> testGroop(@PathVariable String city, @PathVariable String district) {
        RegionDto regionDto = graphService.convertRegionToDto(city, district);
        return graphService.getGraphDtoByRegionDtoAndDateDto(regionDto, new DateDto(null, DateDto.DateType.YEAR));
    }

    @GetMapping("/test/city/{city}/district/{district}/neighborhood/{neighborhood}")
    public List<GraphDto> testDong(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood) {
        RegionDto regionDto = graphService.convertRegionToDto(city, district, neighborhood);
        return graphService.getGraphDtoByRegionDtoAndDateDto(regionDto, new DateDto(null, DateDto.DateType.YEAR));
    }

}
