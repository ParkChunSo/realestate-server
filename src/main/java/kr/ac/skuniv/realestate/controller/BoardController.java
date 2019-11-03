package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.dto.boardDto.AnswerSaveDto;
import kr.ac.skuniv.realestate.domain.dto.boardDto.AnswerUpdateDto;
import kr.ac.skuniv.realestate.domain.dto.boardDto.BoardSaveDto;
import kr.ac.skuniv.realestate.domain.dto.boardDto.BoardUpdateDto;
import kr.ac.skuniv.realestate.domain.entity.Answer;
import kr.ac.skuniv.realestate.domain.entity.Board;
import kr.ac.skuniv.realestate.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by YoungMan on 2019-02-16.
 */

@RestController
@RequestMapping(value = "/realestate/board")
@Log4j2
@CrossOrigin
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation("페이지에 따른 게시판글")
    @GetMapping("/city/{city}/district/{district}/page/{page}")
    public List<Board> getBoardsByPage(@PathVariable String city, @PathVariable String district, @PathVariable int page) {
        return boardService.getBoardsByPage(city, district, page);
    }

//    @ApiOperation("게시판글 번호에 따른 세부 내용")
//    @GetMapping("/detail/{boardNo}")
//    public Board getDetailByBoard(@PathVariable Long boardNo) {
//        return boardService.getBoardById(boardNo);
//    }
//
//    @ApiOperation("제목으로 검색시 게시판글 목록")
//    @GetMapping("/title/{title}")
//    public List<Board> getBoardsByTitle(@PathVariable String title) {
//        return boardService.getBoardsByTitle(title);
//    }

    @ApiOperation("게시판 저장")
    @PostMapping
    public List<Board> saveBoard(@RequestBody BoardSaveDto boardSaveDto) {
        boardService.saveBoard(boardSaveDto);
        List<Board> boardsByPage = boardService.getBoardsByPage(boardSaveDto.getCity(), boardSaveDto.getDistrict(), 1);

        log.warn(boardsByPage.size());

        return boardsByPage;
    }

//    @ApiOperation("게시판 수정")
//    @PutMapping
//    public Board updateBoard(@RequestBody BoardUpdateDto boardUpdateDto) {
//        return boardService.updateBoard(boardUpdateDto);
//    }
//
//    @ApiOperation("게시판 삭제")
//    @DeleteMapping("/{boardNo}")
//    public void deleteBoard(@PathVariable Long boardNo) {
//        boardService.deleteBoard(boardNo);
//    }
//
    @ApiOperation("댓글 저장")
    @PostMapping("/answer")
    public Board saveAnswer(@RequestBody AnswerSaveDto answerSaveDto) {
        log.warn(answerSaveDto.toString());
        return boardService.saveAnswer(answerSaveDto);
    }
//
//    @ApiOperation("댓글 수정")
//    @PutMapping("/answer")
//    public Answer updateAnswer(@RequestBody AnswerUpdateDto answerUpdateDto) {
//        return boardService.updateAnswer(answerUpdateDto);
//    }
//
//    @ApiOperation("댓글 삭제")
//    @DeleteMapping("/answer/{answerNo}")
//    public void deleteAnswer(@PathVariable Long answerNo) {
//        boardService.deleteAnswer(answerNo);
//    }
}