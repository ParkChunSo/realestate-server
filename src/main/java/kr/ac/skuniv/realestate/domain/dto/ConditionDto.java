package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConditionDto {
    private List<MapDto> mapDtos;
    private List<GraphDto> graphDtos;

    public ConditionDto(List<MapDto> mapDtos, List<GraphDto> graphDtos) {
        this.mapDtos = mapDtos;
        this.graphDtos = graphDtos;
    }
}
