package kr.ac.skuniv.realestate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class MapLocationDto {
    private LocationDto rightTop;

    private LocationDto leftBottom;
}
