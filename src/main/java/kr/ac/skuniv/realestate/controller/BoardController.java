package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.dto.AnswerRequestDto;
import kr.ac.skuniv.realestate.domain.dto.BoardRequestDto;
import kr.ac.skuniv.realestate.domain.entity.Board;
import kr.ac.skuniv.realestate.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by YoungMan on 2019-02-16.
 */

@RestController
@RequestMapping(value = "/realestate/board")
public class BoardController {

    private Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation("페이지에 따른 게시판글")
    @GetMapping("/{pageNum}")
    public Page<Board> getBoardsByPage(@PathVariable int pageNum) {
        return boardService.getBoardsByPage(pageNum);
    }

    @ApiOperation("게시판글 번호에 따른 세부 내용")
    @GetMapping("/detail/{boardNum}")
    public Board getDetailByBoard(@PathVariable Long boardNum) {
        return boardService.getDetailByBoard(boardNum);
    }

    @ApiOperation("제목 검색시 게시판글 목록")
    @GetMapping("/{title}")
    public List<Board> getBoardsByTitle(@PathVariable String title) {
        return boardService.getBoardsByTitle(title);
    }

    @ApiOperation("게시판 저장")
    @PostMapping
    public void saveBoard(BoardRequestDto boardRequestDto) {
        boardService.saveBoard(boardRequestDto);
    }

    @ApiOperation("게시판 수정")
    @PutMapping
    public void updateBaord(BoardRequestDto boardRequestDto) {
        boardService.updateBoard(boardRequestDto);
    }

    @ApiOperation("게시판 삭제")
    @DeleteMapping("/{boardNum}")
    public void deleteBoard(@PathVariable Long boardNum) {
        boardService.deleteBoard(boardNum);
    }

    @ApiOperation("댓글 저장")
    @PostMapping
    public void saveAnswer(AnswerRequestDto answerRequestDto) {
        boardService.saveAnswer(answerRequestDto);
    }

    @ApiOperation("댓글 수정")
    @PostMapping
    public void updateAnswer(AnswerRequestDto answerRequestDto) {
        boardService.updateAnswer(answerRequestDto);
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping("/{answerNum}")
    public void deleteAnswer(@PathVariable Long answerNum) {
        boardService.deleteAnswer(answerNum);
    }
}
