package kr.ac.skuniv.realestate.repository;

import kr.ac.skuniv.realestate.domain.entity.RentDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RentDateRepository extends JpaRepository<RentDate, Long> {

    @Query(value = "select building.type, rentdate.date, avg(rentdate.monthlyPrice) from RentDate rentdate, Building building where rentdate.building = building.buildingNo and building.city = :cityCode group by building.type, function('date_format', rentdate.date, '%Y')")
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
    List<Object[]> getByDongNameAndDateOnDay(@Param("cityCode") String cityCode, @Param("groopCode") String groopCode, @Param("dongName") String dongName, @Param("date") LocalDate date);

}
