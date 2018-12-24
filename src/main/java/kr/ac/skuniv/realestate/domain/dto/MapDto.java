package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

@Data
public class MapDto {
    private String name;
    private int population;
    private long volum;

    public MapDto(String name, int population, long volum) {
        this.name = name;
        this.population = population;
        this.volum = volum;
    }
}

