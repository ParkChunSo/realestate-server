package kr.ac.skuniv.realestate.domain.dto;

import kr.ac.skuniv.realestate.domain.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by YoungMan on 2019-02-18.
 */

@Getter
@Setter
public class BoardRequestDto {

    private Long no;
    private String title;
    private String content;
    private String author;

    public BoardRequestDto() {
    }

    @Builder
    public BoardRequestDto(Long no, String title, String content, String author) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
