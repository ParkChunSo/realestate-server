package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by youngman on 2019-01-21.
 */
public class testController {

    @Autowired
    private ConditionService conditionService;

    /*@GetMapping("/getByRegionDtoAndDateDto")
    public Iterable<Building> testQueryDsl() {
        return conditionService.search(1L,"11");
    }*/

    @GetMapping("/test/city/{city}")
    public List<GraphDto> testCity(@PathVariable String city) {
        RegionDto regionDto = conditionService.convertRegionToDto(city);
        return conditionService.getGraphDtoByRegionDtoAndDateDto(regionDto, new DateDto(null, DateDto.DateType.YEAR));
    }

    @GetMapping("/test/city/{city}/district/{district}")
    public List<GraphDto> testGroop(@PathVariable String city, @PathVariable String district) {
        RegionDto regionDto = conditionService.convertRegionToDto(city, district);
        return conditionService.getGraphDtoByRegionDtoAndDateDto(regionDto, new DateDto(null, DateDto.DateType.YEAR));
    }

    @GetMapping("/test/city/{city}/district/{district}/neighborhood/{neighborhood}")
    public List<GraphDto> testDong(@PathVariable String city, @PathVariable String district, @PathVariable String neighborhood) {
        RegionDto regionDto = conditionService.convertRegionToDto(city, district, neighborhood);
        return conditionService.getGraphDtoByRegionDtoAndDateDto(regionDto, new DateDto(null, DateDto.DateType.YEAR));
    }

}
