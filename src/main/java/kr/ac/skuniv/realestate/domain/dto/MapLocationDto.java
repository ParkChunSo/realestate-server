package kr.ac.skuniv.realestate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class MapLocationDto {
    private LocationDto rightTop;

    private LocationDto leftBottom;
}
