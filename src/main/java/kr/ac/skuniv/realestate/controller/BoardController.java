package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.dto.AnswerSaveDto;
import kr.ac.skuniv.realestate.domain.dto.AnswerUpdateDto;
import kr.ac.skuniv.realestate.domain.dto.BoardSaveDto;
import kr.ac.skuniv.realestate.domain.dto.BoardUpdateDto;
import kr.ac.skuniv.realestate.domain.entity.Answer;
import kr.ac.skuniv.realestate.domain.entity.Board;
import kr.ac.skuniv.realestate.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by YoungMan on 2019-02-16.
 */

@RestController
@RequestMapping(value = "/realestate/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation("페이지에 따른 게시판글")
    @GetMapping("/{pageNo}")
    public Page<Board> getBoardsByPage(@PathVariable int pageNo) {
        return boardService.getBoardsByPage(pageNo);
    }

    @ApiOperation("게시판글 번호에 따른 세부 내용")
    @GetMapping("/detail/{boardNo}")
    public Board getDetailByBoard(@PathVariable Long boardNo) {
        return boardService.getBoardById(boardNo);
    }

    @ApiOperation("제목으로 검색시 게시판글 목록")
    @GetMapping("/title/{title}")
    public List<Board> getBoardsByTitle(@PathVariable String title) {
        return boardService.getBoardsByTitle(title);
    }

    @ApiOperation("게시판 저장")
    @PostMapping
    public Board saveBoard(@RequestBody BoardSaveDto boardSaveDto) {
        return boardService.saveBoard(boardSaveDto);
    }

    @ApiOperation("게시판 수정")
    @PutMapping
    public Board updateBoard(@RequestBody BoardUpdateDto boardUpdateDto) {
        return boardService.updateBoard(boardUpdateDto);
    }

    @ApiOperation("게시판 삭제")
    @DeleteMapping("/{boardNo}")
    public void deleteBoard(@PathVariable Long boardNo) {
        boardService.deleteBoard(boardNo);
    }

    @ApiOperation("댓글 저장")
    @PostMapping("/answer")
    public Answer saveAnswer(@RequestBody AnswerSaveDto answerSaveDto) {
        return boardService.saveAnswer(answerSaveDto);
    }

    @ApiOperation("댓글 수정")
    @PutMapping("/answer")
    public Answer updateAnswer(@RequestBody AnswerUpdateDto answerUpdateDto) {
        return boardService.updateAnswer(answerUpdateDto);
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping("/answer/{answerNo}")
    public void deleteAnswer(@PathVariable Long answerNo) {
        boardService.deleteAnswer(answerNo);
    }
}
