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
import kr.ac.skuniv.realestate.service.ConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by youngman on 2019-01-16.
 * DomainRepository + Impl : @Repository 어노테이션을 달지 않아도 스프링이 클래스 이름을 보고 사용자 정의 메서드를 구현하고 있다는 것을 알고 bean 으로 등록
 * QuerydslRepositorySupport : 페이징 처리, 헬퍼 클래스
 */
@SuppressWarnings("Duplicates")
@Component
public class BargainDateRepositoryImpl extends QuerydslRepositorySupport implements BargainDateRepositoryCustom {

    /*
     * https://stackoverflow.com/questions/31335211/autowired-vs-persistencecontext-for-entitymanager-bean
     * https://medium.com/@SlackBeck/jpa-entitymanager%EC%99%80-%EB%8F%99%EC%8B%9C%EC%84%B1-e30f841fcdf8
     * EntityManager 동시성 문제 해결을 위한 어노테이션
     * EntityManager 생성을 Container 에게 위임하고 필요할 때마다 의존성 주입을 받아 사용한다.
     */
    @PersistenceContext
    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(BargainDateRepositoryImpl.class);

    public BargainDateRepositoryImpl() {
        super(BargainDate.class);
    }

    @Override
    @AspectExceptionAnnotation
    public List<GraphTmpDto> getByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto) {
        QBuilding building = QBuilding.building;
        QBargainDate bargainDate = QBargainDate.bargainDate;

        //JPQLQuery 인터페이스 구현체
        JPAQuery<GraphTmpDto> query = new JPAQuery<>(entityManager);
        query.select(Projections.constructor(GraphTmpDto.class, building.type, bargainDate.date, bargainDate.price.avg()))
             .from(bargainDate)
             .join(bargainDate.building, building);//타겟,별칭

        if (regionDto.getRegionType() == RegionDto.RegionType.CITY) {
            query.where(building.city.eq(regionDto.getCityCode()));
        } else if (regionDto.getRegionType() == RegionDto.RegionType.DISTRICT) {
            query.where(building.city.eq(regionDto.getCityCode()), building.groop.eq(regionDto.getGroopCode()));
        } else if (regionDto.getRegionType() == RegionDto.RegionType.NEIGHBORHOOD){
            query.where(building.city.eq(regionDto.getCityCode()), building.groop.eq(regionDto.getGroopCode()), building.dong.eq(regionDto.getDongName()));
        }

        if (dateDto.getDateType() == DateDto.DateType.YEAR) {
            query.groupBy(building.type, bargainDate.date.year());
        } else if (dateDto.getDateType() == DateDto.DateType.MONTH) {//년도 같고 월별로
            query.where(bargainDate.date.year().eq(dateDto.getLocalDate().getYear()))
                 .groupBy(building.type, bargainDate.date.month());
        } else if (dateDto.getDateType() == DateDto.DateType.DAY) {//월 같고 날짜별로
            logger.info("========날짜 테스트==========");
            logger.info(String.valueOf(bargainDate.date.year()));
            logger.info(String.valueOf(dateDto.getLocalDate().getYear()));
            logger.info(bargainDate.date.month().toString());
            logger.info(String.valueOf(dateDto.getLocalDate().getMonthValue()));
            logger.info("========날짜 테스트==========");

            query.where(bargainDate.date.year().eq(dateDto.getLocalDate().getYear()), bargainDate.date.month().eq(dateDto.getLocalDate().getMonthValue()))
                 .groupBy(building.type, bargainDate.date);
        }

        return query.fetch();
    }

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
