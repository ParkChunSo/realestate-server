package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.RentDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentDateRepository extends JpaRepository<RentDate, Long> {
}
