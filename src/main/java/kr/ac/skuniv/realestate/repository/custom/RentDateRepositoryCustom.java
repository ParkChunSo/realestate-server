package kr.ac.skuniv.realestate.repository.custom;

import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;

import java.util.List;

/**
 * Created by youngman on 2019-01-19.
 */
public interface RentDateRepositoryCustom {

    List<GraphTmpDto> getByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto);

    /*@Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode group by building.type, function('date_format', rentdate.date, '%Y')")
    List<Object[]> getByCityCodeAndDateOnYear(@Param("cityCode") String cityCode);

    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode group by building.type, function('date_format', rentdate.date, '%Y')")
    List<Object[]> getByGroopCodeAndDateOnYear(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode);

    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and building.dong = :dongName group by building.type, function('date_format', rentdate.date, '%Y')")
    List<Object[]> getByDongNameAndDateOnYear(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("dongName") String dongName);


    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode and function('date_format', rentdate.date, '%Y' ) = function('date_format', :date, '%Y' ) group by building.type, function('date_format', rentdate.date, '%Y-%m')")
    List<Object[]> getByCityCodeAndDateOnMonth(@Param("cityCode") String cityCode, @Param("date") LocalDate date);

    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and function('date_format', rentdate.date, '%Y' ) = function('date_format', :date, '%Y' ) group by building.type, function('date_format', rentdate.date, '%Y-%m')")
    List<Object[]> getByGroopCodeAndDateOnMonth(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("date") LocalDate date);

    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and building.dong = :dongName and function('date_format', rentdate.date, '%Y' ) = function('date_format', :date, '%Y' ) group by building.type, function('date_format', rentdate.date, '%Y-%m')")
    List<Object[]> getByDongNameAndDateOnMonth(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("dongName") String dongName, @Param("date") LocalDate date);


    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode and function('date_format', rentdate.date, '%Y-%m' ) = function('date_format', :date, '%Y-%m' ) group by building.type, function('date_format', rentdate.date, '%Y-%m-%d')")
    List<Object[]> getByCityCodeAndDateOnDay(@Param("cityCode") String cityCode, @Param("date") LocalDate date);

    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and function('date_format', rentdate.date, '%Y-%m' ) = function('date_format', :date, '%Y-%m' ) group by building.type, function('date_format', rentdate.date, '%Y-%m-%d')")
    List<Object[]> getByGroopCodeAndDateOnDay(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("date") LocalDate date);

    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode and building.groop = :groopCode and building.dong = :dongName and function('date_format', rentdate.date, '%Y-%m' ) = function('date_format', :date, '%Y-%m' ) group by building.type, function('date_format', rentdate.date, '%Y-%m-%d')")
    List<Object[]> getByDongNameAndDateOnDay(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("dongName") String dongName, @Param("date") LocalDate date);*/
}
