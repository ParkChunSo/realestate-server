package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.AnswerRequestDto;
import kr.ac.skuniv.realestate.domain.dto.BoardRequestDto;
import kr.ac.skuniv.realestate.domain.entity.Answer;
import kr.ac.skuniv.realestate.domain.entity.Board;
import kr.ac.skuniv.realestate.exception.UserDefineException;
import kr.ac.skuniv.realestate.repository.AnswerRepository;
import kr.ac.skuniv.realestate.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YoungMan on 2019-02-16.
 */

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final AnswerRepository answerRepository;

    public BoardService(BoardRepository boardRepository, AnswerRepository answerRepository) {
        this.boardRepository = boardRepository;
        this.answerRepository = answerRepository;
    }

    public Board getBoardById(Long boardNo) {
        return boardRepository.findById(boardNo).orElseThrow(() -> new UserDefineException("해당 No의 게시글이 없습니다."));
    }

    public Answer getAnswerById(Long answerNo) {
        return answerRepository.findById(answerNo).orElseThrow(() -> new UserDefineException("해당 No의 게시글이 없습니다."));
    }

    public Page<Board> getBoardsByPage(int pageNum) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, 20, Sort.Direction.DESC, "boardId");
        return boardRepository.findAll(pageRequest);
    }

    public Board getDetailByBoard(Long boardNum) {
        return boardRepository.findById(boardNum).orElseThrow(() -> new UserDefineException("해당 아이디의 게시판글이 없습니다."));
    }

    public List<Board> getBoardsByTitle(String title) {
        return boardRepository.findByTitle(title);
    }

    public void saveBoard(BoardRequestDto boardRequestDto) {
        boardRepository.save(convertDtoToSaveBoard(boardRequestDto));
    }

    public void updateBoard(BoardRequestDto boardRequestDto) {
        boardRepository.save(convertDtoToUpdateBoard(boardRequestDto));
    }

    public void deleteBoard(Long boardNum) {
        Board board = getBoardById(boar)
        boardRepository.delete(board);
    }

    private Board convertDtoToSaveBoard(BoardRequestDto boardRequestDto) {
        return Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .author(boardRequestDto.getAuthor())
                .build();
    }

    private Board convertDtoToUpdateBoard(BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardRequestDto.getNo()).orElseThrow(() -> new UserDefineException("해당 No의 게시글이 없습니다."));
        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());
        return board;
    }

    public void saveAnswer(AnswerRequestDto answerRequestDto) {
        answerRepository.save(convertDtoToSaveAnswer(answerRequestDto));
    }

    public void updateAnswer(AnswerRequestDto answerRequestDto) {
        answerRepository.save(convertDtoToUpdateAnswer(answerRequestDto));
    }

    private Answer convertDtoToSaveAnswer(AnswerRequestDto answerRequestDto) {
        Board board = boardRepository.findById(answerRequestDto.getBoardNum()).orElseThrow(() -> new UserDefineException("해당 No의 게시글이 없습니다."));

        return Answer.builder()
                .content(answerRequestDto.getContent())
                .author(answerRequestDto.getAuthor())
                .board(board)
                .build();
    }

    private Answer convertDtoToUpdateAnswer(AnswerRequestDto answerRequestDto) {
        Answer answer = answerRepository.findById(answerRequestDto.getBoardNum()).orElseThrow(() -> new UserDefineException("해당 No의 게시글이 없습니다."));
        answer.setContent(answerRequestDto.getContent());
        return answer;
    }

    public void deleteAnswer(Long answerNum) {

        answerRepository.delete(answerNum);
    }




}
