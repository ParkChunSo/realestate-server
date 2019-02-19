package kr.ac.skuniv.realestate.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class GraphDto {

    private String dealType;
    private String housingType;
    private List<Double> average;

    public GraphDto() {
    }

    @Builder
    public GraphDto(String dealType, String housingType, List<Double> average) {
        this.dealType = dealType;
        this.housingType = housingType;
        this.average = average;
    }
}