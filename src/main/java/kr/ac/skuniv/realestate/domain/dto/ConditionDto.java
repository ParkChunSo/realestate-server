package kr.ac.skuniv.realestate.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConditionDto {
    private List<MapDto> mapDtos;
    private List<GraphDto> graphDtos;

    @Builder
    public ConditionDto(List<MapDto> mapDtos, List<GraphDto> graphDtos) {
        this.mapDtos = mapDtos;
        this.graphDtos = graphDtos;
    }
}
