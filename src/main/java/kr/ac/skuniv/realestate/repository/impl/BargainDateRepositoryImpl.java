package kr.ac.skuniv.realestate.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.realestate.aop.AspectExceptionAnnotation;
import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.domain.entity.BargainDate;
import kr.ac.skuniv.realestate.domain.entity.QBargainDate;
import kr.ac.skuniv.realestate.domain.entity.QBuilding;
import kr.ac.skuniv.realestate.repository.custom.BargainDateRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by youngman on 2019-01-16.
 * QuerydslRepositorySupport : 페이징 처리, 헬퍼 클래스
 */

@SuppressWarnings("Duplicates")
@Component
public class BargainDateRepositoryImpl extends QuerydslRepositorySupport implements BargainDateRepositoryCustom {

    private Logger logger = LoggerFactory.getLogger(BargainDateRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;
    private QBuilding building = QBuilding.building;
    private QBargainDate bargainDate = QBargainDate.bargainDate;

    public BargainDateRepositoryImpl() {
        super(BargainDate.class);
    }

    @Override
    @AspectExceptionAnnotation
    public List<GraphTmpDto> getByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto) {
        JPAQuery<GraphTmpDto> resultQuery = new JPAQuery<>(entityManager);
        resultQuery = setQuery(resultQuery);
        resultQuery = setQueryByRegionDto(resultQuery, regionDto);
        resultQuery = setQueryByDateDto(resultQuery, dateDto);

        return resultQuery.fetch();
    }

    private JPAQuery<GraphTmpDto> setQuery(JPAQuery<GraphTmpDto> query) {
        return query.select(Projections.constructor(GraphTmpDto.class, building.type, bargainDate.date, bargainDate.price.avg()))
                .from(bargainDate)
                .join(bargainDate.building, building);
    }

    private JPAQuery<GraphTmpDto> setQueryByRegionDto(JPAQuery<GraphTmpDto> query, RegionDto regionDto) {

        if (regionDto.getRegionType() == RegionDto.RegionType.CITY) {
            query.where(building.city.eq(regionDto.getCityCode()));
        } else if (regionDto.getRegionType() == RegionDto.RegionType.DISTRICT) {
            query.where(building.city.eq(regionDto.getCityCode()), building.groop.eq(regionDto.getGroopCode()));
        } else if (regionDto.getRegionType() == RegionDto.RegionType.NEIGHBORHOOD) {
            query.where(building.city.eq(regionDto.getCityCode()), building.groop.eq(regionDto.getGroopCode()), building.dong.eq(regionDto.getDongName()));
        }

        return query;
    }

    private JPAQuery<GraphTmpDto> setQueryByDateDto(JPAQuery<GraphTmpDto> query, DateDto dateDto) {

        if (dateDto.getDateType() == DateDto.DateType.YEAR) {
            query.groupBy(building.type, bargainDate.date.year());
        } else if (dateDto.getDateType() == DateDto.DateType.MONTH) {//년도 같고 월별로
            query.where(bargainDate.date.year().eq(dateDto.getLocalDate().getYear()))
                    .groupBy(building.type, bargainDate.date.month());
        } else if (dateDto.getDateType() == DateDto.DateType.DAY) {//월 같고 날짜별로
            query.where(bargainDate.date.year().eq(dateDto.getLocalDate().getYear()), bargainDate.date.month().eq(dateDto.getLocalDate().getMonthValue()))
                    .groupBy(building.type, bargainDate.date);
        }

        return query;
    }

    /*logger.info("========날짜 테스트==========");
            logger.info(String.valueOf(bargainDate.date.year()));
            logger.info(String.valueOf(dateDto.getLocalDate().getYear()));
            logger.info(bargainDate.date.month().toString());
            logger.info(String.valueOf(dateDto.getLocalDate().getMonthValue()));
            logger.info("========날짜 테스트==========");
    */


    /*@Override
    public List<GraphTmpDto> getByCityCodeAndYear(String cityCode) {
        QBuilding building = QBuilding.building;
        QBargainDate bargainDate = QBargainDate.bargainDate;

        JPAQuery<GraphTmpDto> query = new JPAQuery<>(entityManager);
        return query.select(Projections.constructor(GraphTmpDto.class, building.type, bargainDate.date, bargainDate.price.avg()))
                    .from(bargainDate)
                    .join(bargainDate.building, building)//타겟,별칭
                    .where(building.city.eq(cityCode))
                    .groupBy(building.type, bargainDate.date.year())
                    .fetch();
    }

    @Override
    public List<GraphTmpDto> getByGroopCodeAndYear(String cityCode, String groopCode) {
        QBuilding building = QBuilding.building;
        QBargainDate bargainDate = QBargainDate.bargainDate;

        JPAQuery<GraphTmpDto> query = new JPAQuery<>(entityManager);
        return query.select(Projections.constructor(GraphTmpDto.class, building.type, bargainDate.date, bargainDate.price.avg()))
                    .from(bargainDate)
                    .join(bargainDate.building, building)//타겟,별칭
                    .where(building.city.eq(cityCode), building.groop.eq(groopCode))
                    .groupBy(building.type, bargainDate.date.year())
                    .fetch();
    }

    @Override
    public List<GraphTmpDto> getByDongNameAndYear(String cityCode, String groopCode, String dongName) {
        QBuilding building = QBuilding.building;
        QBargainDate bargainDate = QBargainDate.bargainDate;

        JPAQuery<GraphTmpDto> query = new JPAQuery<>(entityManager);
        return query.select(Projections.constructor(GraphTmpDto.class, building.type, bargainDate.date, bargainDate.price.avg()))
                    .from(bargainDate)
                    .join(bargainDate.building, building)//타겟,별칭
                    .where(building.city.eq(cityCode), building.groop.eq(groopCode), building.dong.eq(dongName))
                    .groupBy(building.type, bargainDate.date.year())
                    .fetch();
    }

    @Override
    public List<GraphTmpDto> getByCityCodeAndMonth(String cityCode, LocalDate date) {
        QBuilding building = QBuilding.building;
        QBargainDate bargainDate = QBargainDate.bargainDate;

        JPAQuery<GraphTmpDto> query = new JPAQuery<>(entityManager);
        return query.select(Projections.constructor(GraphTmpDto.class, building.type, bargainDate.date, bargainDate.price.avg()))
                    .from(bargainDate)
                    .join(bargainDate.building, building)//타겟,별칭
                    .where(building.city.eq(cityCode), bargainDate.date.year().eq(date.getYear()))
                    .groupBy(building.type, bargainDate.date.month())
                    .fetch();
    }

    @Override
    public List<GraphTmpDto> getByCityCodeAndDay(String cityCode, LocalDate date) {
        QBuilding building = QBuilding.building;
        QBargainDate bargainDate = QBargainDate.bargainDate;

        JPAQuery<GraphTmpDto> query = new JPAQuery<>(entityManager);
        return query.select(Projections.constructor(GraphTmpDto.class, building.type, bargainDate.date, bargainDate.price.avg()))
                    .from(bargainDate)
                    .join(bargainDate.building, building)//타겟,별칭
                    .where(building.city.eq(cityCode), bargainDate.date.year().eq(date.getYear()), bargainDate.date.month().eq(date.getDayOfMonth()))
                    .groupBy(building.type, bargainDate.date)
                    .fetch();
    }*/

}
