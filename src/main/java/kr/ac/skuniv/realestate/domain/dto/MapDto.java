package kr.ac.skuniv.realestate.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapDto {
    private String name;
    private int population;
    private long volum;

    @Builder
    public MapDto(String name, int population, long volum) {
        this.name = name;
        this.population = population;
        this.volum = volum;
    }
}

