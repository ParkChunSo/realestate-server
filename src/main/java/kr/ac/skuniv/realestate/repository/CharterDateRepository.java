package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.CharterDate;
import kr.ac.skuniv.realestate.repository.custom.CharterDateRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharterDateRepository extends JpaRepository<CharterDate, Long>, CharterDateRepositoryCustom {
}
