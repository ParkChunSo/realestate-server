package kr.ac.skuniv.realestate.controller;

import kr.ac.skuniv.realestate.service.SearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
