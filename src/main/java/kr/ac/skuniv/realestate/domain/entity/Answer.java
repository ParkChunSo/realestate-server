package kr.ac.skuniv.realestate.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by YoungMan on 2019-02-16.
 */

@Entity
@Getter
@Setter
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String content;

    private String author;

    @CreatedDate
    private String registerDate;

    @LastModifiedDate
    private Date modifyDate;

    @ManyToOne
    private Board board;

    @Builder
    public Answer(String content, String author, Board board) {
        this.content = content;
        this.author = author;
        this.board = board;
    }
}
