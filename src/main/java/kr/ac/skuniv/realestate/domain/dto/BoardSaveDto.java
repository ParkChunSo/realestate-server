package kr.ac.skuniv.realestate.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by YoungMan on 2019-02-19.
 */

@Getter
@Setter
public class BoardSaveDto {

    private String title;
    private String content;
    private String author;

    public BoardSaveDto() {
    }

    @Builder
    public BoardSaveDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
