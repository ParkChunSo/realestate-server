package kr.ac.skuniv.realestate.domain.dto;

import kr.ac.skuniv.realestate.domain.entity.Building;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SearchTmpDto {
    private List<Building> buildings;

    private List<LocationDto> options;
}