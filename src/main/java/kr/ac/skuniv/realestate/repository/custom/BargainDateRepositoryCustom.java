package kr.ac.skuniv.realestate.repository.custom;

import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.domain.entity.Building;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by youngman on 2019-01-16.
 * Predicate : QueryDsl 의 쿼리
 */
public interface BargainDateRepositoryCustom {

    List<GraphTmpDto> getByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto);

    /*List<GraphTmpDto> getByCityCodeAndYear(String cityCode);

    List<GraphTmpDto> getByGroopCodeAndYear(String cityCode, String groopCode);

    List<GraphTmpDto> getByDongNameAndYear(String cityCode, String groopCode, String dongName);

    List<GraphTmpDto> getByCityCodeAndMonth(String cityCode, LocalDate date);

//    List<GraphTmpDto> getByGroopCodeAndMonth(String cityCode, String groopCode, LocalDate date);

//    List<GraphTmpDto> getByDongNameAndMonth(String cityCode, String groopCode, String dongName, LocalDate date);

    List<GraphTmpDto> getByCityCodeAndDay(String cityCode, LocalDate date);

//    List<GraphTmpDto> getByGroopCodeAndDay(String cityCode, String groopCode, LocalDate date);

//    List<GraphTmpDto> getByDongNameAndDay(String cityCode, String groopCode, String dongName, LocalDate date);

    @Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode group by building.type, function('date_format', bargaindate.date, '%Y')")
    List<Object[]> getByCityCodeAndDateOnYear(@Param("cityCode") String cityCode);

    @Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode group by building.type, function('date_format', bargaindate.date, '%Y')")
    List<Object[]> getByGroopCodeAndDateOnYear(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode);

    @Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and building.dong = :dongName group by building.type, function('date_format', bargaindate.date, '%Y')")
    List<Object[]> getByDongNameAndDateOnYear(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("dongName") String dongName);

    */
    /*@Query(value = "select new kr.ac.skuniv.realestate.domain.dto.GraphTmpDto(:dealType, building.type, bargaindate.date, avg(bargaindate.price)) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode group by building.type, function('date_format', bargaindate.date, '%Y')")
    List<GraphTmpDto> getByRegionDtoAndDateDto(@Param("cityCode") String cityCode, @Param("dealType") String dealType);*/


    /*@Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode and function('date_format', bargaindate.date, '%Y' ) = function('date_format', :date, '%Y' ) group by building.type, function('date_format', bargaindate.date, '%Y-%m')")
    List<Object[]> getByCityCodeAndDateOnMonth(@Param("cityCode") String cityCode, @Param("date") LocalDate date);

    @Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and function('date_format', bargaindate.date, '%Y' ) = function('date_format', :date, '%Y' ) group by building.type, function('date_format', bargaindate.date, '%Y-%m')")
    List<Object[]> getByGroopCodeAndDateOnMonth(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("date") LocalDate date);

    @Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and building.dong = :dongName and function('date_format', bargaindate.date, '%Y' ) = function('date_format', :date, '%Y' ) group by building.type, function('date_format', bargaindate.date, '%Y-%m')")
    List<Object[]> getByDongNameAndDateOnMonth(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("dongName") String dongName, @Param("date") LocalDate date);


    @Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode and function('date_format', bargaindate.date, '%Y-%m' ) = function('date_format', :date, '%Y-%m' ) group by building.type, function('date_format', bargaindate.date, '%Y-%m-%d')")
    List<Object[]> getByCityCodeAndDateOnDay(@Param("cityCode") String cityCode, @Param("date") LocalDate date);

    @Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and function('date_format', bargaindate.date, '%Y-%m' ) = function('date_format', :date, '%Y-%m' ) group by building.type, function('date_format', bargaindate.date, '%Y-%m-%d')")
    List<Object[]> getByGroopCodeAndDateOnDay(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("date") LocalDate date);

    @Query(value = "select building.type, bargaindate.date, avg(bargaindate.price) from BargainDate bargaindate, Building building where bargaindate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and building.dong = :dongName and function('date_format', bargaindate.date, '%Y-%m' ) = function('date_format', :date, '%Y-%m' ) group by building.type, function('date_format', bargaindate.date, '%Y-%m-%d')")
    List<Object[]> getByDongNameAndDateOnDay(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("dongName") String dongName, @Param("date") LocalDate date);*/
}
