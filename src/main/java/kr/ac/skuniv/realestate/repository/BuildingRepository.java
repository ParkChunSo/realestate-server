package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.Building;
import kr.ac.skuniv.realestate.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long>, BuildingRepositoryCustom {

    List<Building> findByLatitudeBetweenAndLongitudeBetween(BigDecimal leftBottomX, BigDecimal rightTopX, BigDecimal leftBottomY, BigDecimal rightTopY);
}