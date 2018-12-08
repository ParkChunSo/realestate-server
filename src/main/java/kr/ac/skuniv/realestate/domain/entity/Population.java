package kr.ac.skuniv.realestate.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "web_population_tbl")
public class Population {
    @Id
    private int id;

    private int code;

    private int population;
/*
    @OneToMany(fetch = FetchType.LAZY)
    private List<Forsale> forsales;*/
}
