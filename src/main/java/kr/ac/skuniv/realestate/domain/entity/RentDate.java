package kr.ac.skuniv.realestate.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "rent_date")
public class RentDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Column(name = "guarantee_price")
    private Double guaranteePrice;

    @Column(name = "monthly_price")
    private Double monthlyPrice;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingNo")
    private Building building;

}
