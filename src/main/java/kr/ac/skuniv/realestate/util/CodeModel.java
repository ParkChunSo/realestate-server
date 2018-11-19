package kr.ac.skuniv.realestate.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class CodeModel {
    private String name;
    private int code;
    private String population;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }
}
