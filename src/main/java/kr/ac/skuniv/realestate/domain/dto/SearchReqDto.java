package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchReqDto {

    private List<String> dealType;

    private List<String> housingType;

    private MapLocationDto mapLocation;

    private List<LocationDto> options;
}
