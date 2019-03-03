package kr.ac.skuniv.realestate.repository.custom;

import kr.ac.skuniv.realestate.domain.dto.*;

import java.util.List;

/**
 * Created by youngman on 2019-01-19.
 */

public interface RentDateRepositoryCustom {

    List<GraphTmpDto> getByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto);

}
