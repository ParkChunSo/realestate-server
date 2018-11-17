package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.Forsale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForsaleRepository extends JpaRepository<Forsale, Long> {

    @Query("select avg(f.price) from Forsale f where f.code=1122333")
    int getPriceSum();

    @Query("select f from Forsale  f where f.code=:code")
    List<Forsale> getCode(@Param("code") int code);
}
