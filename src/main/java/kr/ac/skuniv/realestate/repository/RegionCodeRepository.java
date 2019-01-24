package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.RegionCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface RegionCodeRepository extends CrudRepository<RegionCode, String> {
}
