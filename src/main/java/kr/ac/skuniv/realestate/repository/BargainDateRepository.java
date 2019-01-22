package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.BargainDate;
import kr.ac.skuniv.realestate.repository.custom.BargainDateRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BargainDateRepository extends JpaRepository<BargainDate, Long>, BargainDateRepositoryCustom {
}