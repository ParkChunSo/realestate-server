package kr.ac.skuniv.realestate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by youngman on 2019-01-22.
 */
@Entity
@Getter
@Setter
@Table(name = "cosmos_member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "member_name")
    private String name;

    private Integer age;
}
