package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.dto.SearchResDto;
import kr.ac.skuniv.realestate.domain.dto.SearchTmpDto;
import kr.ac.skuniv.realestate.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungMan on 2019-02-16.
 */

@RestController
@RequestMapping(value = "/realestate/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @ApiOperation("매매 정보 검색")
    @PostMapping()
    public List<SearchTmpDto> searchBuilding(@RequestBody SearchReqDto searchReqDto){
        List<SearchTmpDto> searchTmpDtos = searchService.buildingFiltering(searchReqDto);
        return searchService.optionFiltering(searchTmpDtos, searchReqDto.getOptions());
    }
}
