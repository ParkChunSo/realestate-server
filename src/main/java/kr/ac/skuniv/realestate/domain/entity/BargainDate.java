package kr.ac.skuniv.realestate.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "bargain_date")
public class BargainDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Column(name = "price")
    private Double price;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingNo")
    private Building building;

}
