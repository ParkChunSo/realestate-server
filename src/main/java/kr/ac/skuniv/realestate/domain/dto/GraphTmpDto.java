package kr.ac.skuniv.realestate.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
public class GraphTmpDto {

    private String dealType;
    private String housingType;
    private Date date;
    private Double average;

    public GraphTmpDto() {
    }

    @QueryProjection
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

    @Override
    public String toString() {
        return "GraphTmpDto{" +
                "housingType='" + housingType + '\'' +
                ", date=" + date +
                ", average=" + average +
                '}';
    }
}
