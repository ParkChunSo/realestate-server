package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

@Data
public class MapDto {
    private String name;  // 지역 이름
    private int population;  // 인구수
    private int volum;  // 거래량
}
