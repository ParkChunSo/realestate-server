package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

@Data
public class MapLocationDto {

    private LocationDto rightTop;

    private LocationDto leftBottom;
}
