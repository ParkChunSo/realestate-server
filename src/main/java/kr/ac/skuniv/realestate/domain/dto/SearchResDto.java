package kr.ac.skuniv.realestate.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResDto {
    // 시
    private String city;
    // 군/구
    private String groop;
    // 동
    private String dong;
    // 건물 이름
    private String name;
    // 면적
    private double area;
    //층
    private int floor;
    // (아파트 or 오피스텔 or 주택)
    private int type;
    //
    private String buildingNum;
    // 건축년도
    private String constructorYear;
    // 최신 거래 가격
    private String price;
    // 보증금
    private String deposit;
    // 전세 or 월세 or 매매
    private String dealType;
    // 위도
    private double latitude;
    // 경도
    private double longitude;

}
