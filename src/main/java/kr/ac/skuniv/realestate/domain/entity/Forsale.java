package kr.ac.skuniv.realestate.domain.entity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Forsale {
    @Id @GeneratedValue
    private Long id;

    private int code;

    private String housingType;

    private String dealType;

    private String name;

    private int price;

    @Temporal(value = TemporalType.DATE)
    private Date date;
}