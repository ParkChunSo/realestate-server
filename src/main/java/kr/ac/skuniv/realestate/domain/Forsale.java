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
public class Forsale {
    @Id
    private Long id;

    private int code;

    private String type;

    private int price;

    private Date date;
}
