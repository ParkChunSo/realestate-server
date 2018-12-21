package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.Population;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PopulationRepository extends JpaRepository<Population, Long> {

    @Query("select p from Population p where p.code=:code")
    int getfind(@Param("code") int code);
}

