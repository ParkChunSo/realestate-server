package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by YoungMan on 2019-02-18.
 */

public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findByTitle(@Param("title") String title);
}
