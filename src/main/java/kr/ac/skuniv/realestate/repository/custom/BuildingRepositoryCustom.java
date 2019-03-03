package kr.ac.skuniv.realestate.repository.custom;

import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.dto.SearchResDto;

import java.util.List;

public interface BuildingRepositoryCustom {
    List<SearchResDto> getDealBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto);

    List<SearchResDto> getLeaseBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto);

    List<SearchResDto> getRentBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto);
}
