package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.WebForsaleTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<WebForsaleTbl, Long> {

    @Query("select f from WebForsaleTbl  f where f.code=:code")
    List<WebForsaleTbl> getCode(@Param("code") int code);

    List<WebForsaleTbl> findAllByCode(int code);
}
