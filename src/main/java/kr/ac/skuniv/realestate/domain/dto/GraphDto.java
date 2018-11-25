package kr.ac.skuniv.realestate.domain.dto;

import java.util.List;

public class GraphDto {
    private String dealType;  // 월세 or 전세
    private String housingType; // 아파트 or 오피스텔
    private List<Integer> average; // 평균거래가
}