package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConditionDto {
    private List<MapDto> mapDtos;
    private List<GraphDto> graphDtos;
}
