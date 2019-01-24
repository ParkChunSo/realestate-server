package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}