package kr.ac.skuniv.realestate.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "building")
public class Building {

    @Id
    @Column(name = "building_no", nullable = false)
    private Long buildingNo;

    @Column(nullable = false)
    private double area;

    @Column(name = "building_num", nullable = false)
    private String buildingNum;

//    @Column(nullable = false)
    private String city;

//    @Column(nullable = false)
    private String groop;

//    @Column(nullable = false)
    private String dong;

//    @Column(name = "construct_year", nullable = false)
    private String constructYear;

//    @Column(nullable = false)
    private int floor;

//    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "building")
    private List<BargainDate> bargainDates;

    @OneToMany(mappedBy = "building")
    private List<CharterDate> charterDates;

    @OneToMany(mappedBy = "building")
    private List<RentDate> rentDates;

}

