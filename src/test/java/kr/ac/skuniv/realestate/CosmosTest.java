package kr.ac.skuniv.realestate;

import kr.ac.skuniv.realestate.domain.dto.AnswerSaveDto;
import kr.ac.skuniv.realestate.domain.dto.AnswerUpdateDto;
import kr.ac.skuniv.realestate.domain.dto.BoardSaveDto;
import kr.ac.skuniv.realestate.domain.dto.BoardUpdateDto;
import kr.ac.skuniv.realestate.domain.entity.Board;
import kr.ac.skuniv.realestate.service.BoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by youngman on 2019-01-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CosmosTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void saveBoard() {
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .title("title1")
                .content("content")
                .author("author1")
                .build();

        Board board = boardService.saveBoard(boardSaveDto);
        System.out.println("============================");
        System.out.println(board.toString());

    }

    @Test
    public void updateBoard() {
        BoardUpdateDto boardUpdateDto = BoardUpdateDto.builder()
                .no(1L)
                .title("update_title1")
                .content("update_content")
                .author("author1")
                .build();

        Board board = boardService.updateBoard(boardUpdateDto);
        System.out.println("============================");
        System.out.println(board.toString());
    }

    @Test
    public void getBoardById() {
        Board board = boardService.getBoardById(14L);
        System.out.println("============================");
        System.out.println(board.toString());
    }

    @Test
    public void deleteBoard() {
        boardService.deleteBoard(2L);
    }

    @Test
    public void getBoardsByTitle() {
        List<Board> boards = boardService.getBoardsByTitle("it");
        System.out.println("============================");
        boards.forEach(s-> System.out.println(s.toString()));
    }

    @Test
    public void getBoardsByPage() {
        Page<Board> boards = boardService.getBoardsByPage(1);
        System.out.println("============================");
        boards.forEach(s-> System.out.println(s.toString()));
    }

    @Test
    public void saveAnswer() {
        AnswerSaveDto answerSaveDto = AnswerSaveDto.builder()
                .content("answer1")
                .author("author1")
                .boardNo(3L)
                .build();

        boardService.saveAnswer(answerSaveDto);
    }

    @Test
    public void updateAnswer() {
        AnswerUpdateDto answerUpdateDto = AnswerUpdateDto.builder()
                .content("update_answer1")
                .no(1L)
                .boardNo(2L)
                .build();
        boardService.updateAnswer(answerUpdateDto);
    }

    @Test
    public void deleteAnswer() {
        boardService.deleteAnswer(1L);
    }
}
