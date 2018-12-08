package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConditionDto {
    private List<MapDto> mapDtos;
    private List<GraphDto> graphDtos;

    public ConditionDto(List<MapDto> _mapDtos, List<GraphDto> _graphDtos) {
        this.mapDtos = _mapDtos;
        this.graphDtos = _graphDtos;
    }
}
