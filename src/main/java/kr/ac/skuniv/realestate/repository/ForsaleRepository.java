package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.Forsale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface ForsaleRepository extends JpaRepository<Forsale, Long> {

    @Query("select f from Forsale  f where f.code like :code")
    List<Forsale> getCode(@Param("code") String code);

    @Query(value = "select f.code, function('date_format', f.date, '%Y-%m'), avg(f.price), count(f.code) from Forsale f  where f.code like concat(:code, '%') group by function('date_format', f.date, '%Y-%m')")
    List<Object> getByCode(@Param("code") int code);

    @Query(value = "select f.dealType, f.housingType, f.date, avg(f.price) from Forsale f  where f.code like concat(:code, '%') group by f.dealType, f.housingType, function('date_format', f.date, '%Y') order by f.dealType, f.housingType")
    List<Object[]> getByCodeAndDateOnYear(@Param("code") int code);

//    @Query("select avg (a1.price + 0) , avg (a2.price+ 0), avg (a3.price+ 0) from Forsale a1, Forsale a2, Forsale a3 where a1.dealType = '월세' and a1.housingType = '아파트' " +
//            "and a2.dealType = '전세' and a2.housingType = '아파트' and a3.dealType = '매매' and a3.housingType = '아파트'")
//    //@Query("select avg (a1.price) , avg (a2.price), avg (a3.price) from Forsale a1, Forsale a2, Forsale a3 where price select a1 from Forsale a1 ")
//    Object getAve();

//    @Query("select f from Forsale where ")
    @Query(value = "select f.dealType, f.housingType, f.date, avg(f.price) from Forsale f  where f.code like concat(:code, '%') and f.date = function('date_format', :date, '%Y' ) group by f.dealType, f.housingType, function('date_format', f.date, '%Y-%m')")
    List<Object[]> getByCodeAndDateOnMonth(@Param("code") int code, @Param("date") Date date);

    @Query(value = "select f.dealType, f.housingType, f.date, avg(f.price) from Forsale f  where f.code like concat(:code, '%') and f.date = :date group by f.dealType, f.housingType, function('date_format', f.date, '%Y-%m-%d')")
    List<Object[]> getByCodeAndDateOnDay(@Param("code") int code, @Param("date") Date date);
}
