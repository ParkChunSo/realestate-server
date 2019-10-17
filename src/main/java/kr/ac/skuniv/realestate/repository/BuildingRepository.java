package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

//    @Query(value = "select building from Building building where building.latitude < :#{#searchReqDto.mapLocation.rightTop.latitude} " +
//            "and building.latitude > :#{#searchReqDto.mapLocation.leftBottom.latitude} " +
//            "and building.longitude < :#{#searchReqDto.mapLocation.rightTop.longitude} " +
//            "and building.longitude > :#{#searchReqDto.mapLocation.leftBottom.longitude}")
//    List<Building> searchBuilding(@Param(value = "searchReqDto") SearchReqDto searchReqDto);


    @Query(value = "select building from Building building where building.dong like concat('%', :#{#searchReqDto.address}, '%') ")
    List<Building> searchBuildingTest(@Param(value = "searchReqDto") SearchReqDto searchReqDto);

//    @Query(value = " select building from Building building where building.latitude between :#{#searchReqDto.mapLocation.leftBottom.latitude} and :#{#searchReqDto.mapLocation.rightTop.latitude} " +
//            "and building.longitude between :#{#searchReqDto.mapLocation.leftBottom.longitude} and :#{#searchReqDto.mapLocation.rightTop.longitude} and building.type in 'opistel' ")
//    List<Building> searchBuilding(@Param(value = "searchReqDto") SearchReqDto searchReqDto);
}