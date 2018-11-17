package kr.ac.skuniv.realestate.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Population {
    @Id
    private int id;

    private int code;

    private int population;
}
