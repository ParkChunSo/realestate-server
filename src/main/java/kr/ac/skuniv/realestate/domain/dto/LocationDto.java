package kr.ac.skuniv.realestate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @AllArgsConstructor
public class LocationDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
