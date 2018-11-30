package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class GraphDto {
    private String dealType;
    private String housingType;
    private List<Integer> average;
}