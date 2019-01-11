package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BuildingRepository extends JpaRepository<Building, Long>, QuerydslPredicateExecutor<Building> {

}
