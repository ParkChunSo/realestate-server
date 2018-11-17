package kr.ac.skuniv.realestate.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WebPopulationTbl {

    @Id
    private Long id;

    private int code;

    private int population;
}
