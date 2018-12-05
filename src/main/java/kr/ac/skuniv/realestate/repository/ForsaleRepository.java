package kr.ac.skuniv.realestate.repository;

import io.swagger.models.auth.In;
import kr.ac.skuniv.realestate.domain.entity.Forsale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface ForsaleRepository extends JpaRepository<Forsale, Long> {

    @Query("select f from Forsale  f where f.code like :code")
    List<Forsale> getCode(@Param("code") int code);

    List<Forsale> findByCode(int code);

    @Query("select avg (a1.price + 0) , avg (a2.price+ 0), avg (a3.price+ 0) from Forsale a1, Forsale a2, Forsale a3 where a1.dealType = '월세' and a1.housingType = '아파트' " +
            "and a2.dealType = '전세' and a2.housingType = '아파트' and a3.dealType = '매매' and a3.housingType = '아파트'")
    //@Query("select avg (a1.price) , avg (a2.price), avg (a3.price) from Forsale a1, Forsale a2, Forsale a3 where price select a1 from Forsale a1 ")
    Object getAve();

//    @Query("select f from Forsale where ")
}
