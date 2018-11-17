package kr.ac.skuniv.realestate.domain;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "web_forsale_tbl")
public class WebForsaleTbl {
    @Id
    private Long id;

    private int code;

    private String housing_type;

    private String deal_type;

    private String name;

    private int price;

    private Date date;
}
