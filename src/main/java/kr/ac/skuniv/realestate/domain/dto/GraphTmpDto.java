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

    public GraphTmpDto(String _dealType, String _housingType, Date _date, Double _average) {
        this.dealType = _dealType;
        this.housingType = _housingType;
        this.date = _date;
        this.average = _average;
    }

}
