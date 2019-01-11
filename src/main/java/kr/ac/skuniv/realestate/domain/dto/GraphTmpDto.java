package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GraphTmpDto {
    private String dealType;
    private String housingType;
    private Date date;
    private Double average;

    public GraphTmpDto(String housingType, Date date, Double average) {
        this.housingType = housingType;
        this.date = date;
        this.average = average;
    }

    public GraphTmpDto(String dealType, String housingType, Date date, Double average) {
        this.dealType = dealType;
        this.housingType = housingType;
        this.date = date;
        this.average = average;
    }

}
