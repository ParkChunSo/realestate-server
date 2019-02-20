package kr.ac.skuniv.realestate.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by YoungMan on 2019-02-16.
 */

@Entity
@Getter
@Setter
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String title;

    private String content;

    private String author;

    @CreatedDate
    private Date registerDate;

    @LastModifiedDate
    private Date modifyDate;

    @OneToMany
    private List<Answer> answers;

    @Builder
    public Board(String title, String content, String author, List<Answer> answers) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.answers = answers;
    }
}
