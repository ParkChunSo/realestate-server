package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.BargainDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BargainDateRepository extends JpaRepository<BargainDate, Long> {

    @Query(value = "select bargaindate.date, avg(bargaindate.price), building.type from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city like concat(:code, '%') group by building.type, function('date_format', bargaindate.date, '%Y')")
    List<Object[]> getByCodeAndDateOnYear(@Param("code") String code);

    /*@Query(value = "select bargaindate.date, avg(bargaindate.price), building.type from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city like concat(:code, '%') group by building.type, function('date_format', bargaindate.date, '%Y')")
    List<Object[]> getByCodeAndDateOnMonth(@Param("code") String code, @Param("date") LocalDate date);

    @Query(value = "select bargaindate.date, avg(bargaindate.price), building.type from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city like concat(:code, '%') group by building.type, function('date_format', bargaindate.date, '%Y')")
    List<Object[]> getByCodeAndDateOnDay(@Param("code") String code, @Param("date") LocalDate date);*/

}
