package kr.ac.skuniv.realestate.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by YoungMan on 2019-02-18.
 */

@Getter
@Setter
public class AnswerRequestDto {

    private String content;
    private String author;
    private Long boardNum;

}
