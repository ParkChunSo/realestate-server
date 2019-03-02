package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
