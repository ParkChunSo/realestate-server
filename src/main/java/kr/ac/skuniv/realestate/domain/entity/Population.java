package kr.ac.skuniv.realestate.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Population {
    @Id
    private int id;

    private int code;

    private int population;
/*
    @OneToMany(fetch = FetchType.LAZY)
    private List<Forsale> forsales;*/
}
