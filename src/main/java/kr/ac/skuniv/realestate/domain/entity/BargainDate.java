package kr.ac.skuniv.realestate.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Date;

@Entity//DB 에 등록될 객체임을 명시
@Getter
@Setter
@Table(name = "bargain_date")//DB 테이블 이름 명시
public class BargainDate {

    @Id//PK
    @GeneratedValue(strategy = GenerationType.AUTO)//PK 생성전략
    private Long id;

    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Column(name = "price")
    private Double price;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingNo")
    private Building building;

}
