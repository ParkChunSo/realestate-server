package kr.ac.skuniv.realestate.domain.entity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "web_forsale_tbl")
public class Forsale {
    @Id
    private Long id;

    private int code;

    private String housingType;

    private String dealType;

    private String name;

    private int price;

    private Date date;
}
