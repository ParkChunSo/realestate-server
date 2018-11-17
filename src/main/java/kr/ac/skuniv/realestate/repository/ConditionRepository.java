package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.WebForsaleTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<WebForsaleTbl, Long> {

    @Query("select avg(f.price) from WebForsaleTbl f where f.code=1122333")
    int getPriceSum();

    List<WebForsaleTbl> findAllByCode(int code);
}
