package kr.ac.skuniv.realestate.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
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
    private String type;
    //
    private String buildingNum;
    // 건축년도
    private String constructorYear;
    // 최신 거래 가격
    private double price;
    // 보증금
    private double deposit;
    // 전세 or 월세 or 매매
    private String dealType;
    // 위도
    private BigDecimal latitude;
    // 경도
    private BigDecimal longitude;

    private Date date;

    /*@QueryProjection
    public SearchResDto(String city, String groop, String dong, String name, double area, int floor, String type, String buildingNum, String constructorYear, double price, double deposit, String dealType,Date date, BigDecimal latitude, BigDecimal longitude) {
        this.city = city;
        this.groop = groop;
        this.dong = dong;
        this.name = name;
        this.area = area;
        this.floor = floor;
        this.type = type;
        this.buildingNum = buildingNum;
        this.constructorYear = constructorYear;
        this.price = price;
        this.deposit = deposit;
        this.dealType = dealType;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @QueryProjection
    public SearchResDto(String city, String groop, String dong, String name, double area, int floor, String type, String buildingNum, String constructorYear, double price,String dealType,Date date, BigDecimal latitude, BigDecimal longitude) {
        this.city = city;
        this.groop = groop;
        this.dong = dong;
        this.name = name;
        this.area = area;
        this.floor = floor;
        this.type = type;
        this.buildingNum = buildingNum;
        this.constructorYear = constructorYear;
        this.price = price;
        this.dealType = dealType;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }*/

/*    @QueryProjection
    public SearchResDto(String city, String groop, String dong, String name, double area, int floor, String type, String buildingNum, String constructorYear, double price, String deposit, String dealType, double latitude, double longitude) {
        this.city = city;
        this.groop = groop;
        this.dong = dong;
        this.name = name;
        this.area = area;
        this.floor = floor;
        this.type = type;
        this.buildingNum = buildingNum;
        this.constructorYear = constructorYear;
        this.price = price;
        this.deposit = deposit;
        this.dealType = dealType;
        this.latitude = latitude;
        this.longitude = longitude;
    }*/

    @QueryProjection
    public SearchResDto(String city, String groop, String dong, String name, double area, int floor, String type, String buildingNum, String constructorYear, double price, BigDecimal latitude, BigDecimal longitude) {
        this.city = city;
        this.groop = groop;
        this.dong = dong;
        this.name = name;
        this.area = area;
        this.floor = floor;
        this.type = type;
        this.buildingNum = buildingNum;
        this.constructorYear = constructorYear;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
