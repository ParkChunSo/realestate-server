package kr.ac.skuniv.realestate.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class WebForsaleTbl {
    @Id
    private Long id;

    private int code;

    private String type;

    private int price;

    private Date date;
}
