package kr.ac.skuniv.realestate.repository.custom;

import kr.ac.skuniv.realestate.domain.dto.*;
import kr.ac.skuniv.realestate.domain.entity.BargainDate;
import kr.ac.skuniv.realestate.domain.entity.CharterDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by youngman on 2019-01-19.
 */

public interface CharterDateRepositoryCustom {

    List<GraphTmpDto> getByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto);

    List<CharterDate> getBuildingByAddressAndHousingType(SearchReqDto searchReqDto);

}
