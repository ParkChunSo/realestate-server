package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.Forsale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForsaleRepository extends JpaRepository<Forsale, Long> {

    @Query("select f from Forsale  f where f.code like :code")
    List<Forsale> getCode(@Param("code") int code);

    List<Forsale> findByCode(int code);

    @Query(value = "select f.dealType, f.housingType, function('date_format', f.date, '%Y'), avg(f.price) from Forsale f  where f.code like concat(:code, '%') group by f.dealType, f.housingType, function('date_format', f.date, '%Y') order by f.dealType, f.housingType")
    List<Object[]> getByCodeAndDateOnYear(@Param("code") int code);

    @Query(value = "select f.code , p.population, count(f) \n from Forsale f, Population p where f.code = p.code and f.code like concat(:code, '%') group by f.code")
    List<Object[]> getMapDtoByCode(@Param("code") int code);
}
