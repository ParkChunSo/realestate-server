package kr.ac.skuniv.realestate.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
/*@SqlResultSetMapping(
        name="ProductOrderedMemberMapping",
        classes = @ConstructorResult(
                targetClass = GraphTmpDto.class,
                columns = {
                        @ColumnResult(name="dealType", type = String.class),
                        @ColumnResult(name="housingType", type = String.class),
                        @ColumnResult(name="date", type = Date.class),
                        @ColumnResult(name="average", type = Integer.class),
                })
)*/
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "web_forsale_tbl")
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
