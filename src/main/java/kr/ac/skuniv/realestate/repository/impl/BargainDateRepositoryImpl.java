package kr.ac.skuniv.realestate.repository.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.LiteralExpression;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.realestate.aop.AspectExceptionAnnotation;
import kr.ac.skuniv.realestate.domain.dto.*;
import kr.ac.skuniv.realestate.domain.entity.BargainDate;
import kr.ac.skuniv.realestate.domain.entity.QBuilding;
import kr.ac.skuniv.realestate.repository.custom.BargainDateRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static kr.ac.skuniv.realestate.domain.entity.QBargainDate.bargainDate;
import static kr.ac.skuniv.realestate.domain.entity.QCharterDate.charterDate;
import static kr.ac.skuniv.realestate.domain.entity.QRentDate.rentDate;

/**
 * Created by youngman on 2019-01-16.
 */

@SuppressWarnings("Duplicates")

@Component
public class BargainDateRepositoryImpl extends QuerydslRepositorySupport implements BargainDateRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    private QBuilding building = QBuilding.building;

    public BargainDateRepositoryImpl() {
        super(BargainDate.class);
    }

    @Override
    @AspectExceptionAnnotation
    public List<GraphTmpDto> getByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto) {
        JPAQuery<GraphTmpDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery);
        jpaQuery = setQueryByRegionDto(jpaQuery, regionDto);
        jpaQuery = setQueryByDateDto(jpaQuery, dateDto);

        return jpaQuery.fetch();
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
        } else if (dateDto.getDateType() == DateDto.DateType.MONTH) {//연도 같고 월별로
            query.where(bargainDate.date.year().eq(dateDto.getLocalDate().getYear()))
                    .groupBy(building.type, bargainDate.date.month());
        } else if (dateDto.getDateType() == DateDto.DateType.DAY) {//월 같고 날짜별로
            query.where(bargainDate.date.year().eq(dateDto.getLocalDate().getYear()), bargainDate.date.month().eq(dateDto.getLocalDate().getMonthValue()))
                    .groupBy(building.type, bargainDate.date);
        }

        return query;
    }

    /*public static Predicate search(Long buildingNo, String city) {
        QBuilding building = QBuilding.building;

        BooleanBuilder builder = new BooleanBuilder();
        if(buildingNo != null) {
            builder.and(building.buildingNo.eq(buildingNo));
        }
        if(city != null) {
            builder.and(building.city.eq(city));
        }
        return builder;
    }*/

 /*   @Override
    public List<SearchResDto> getDealBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto){
        JPAQuery<SearchResDto> jpaQuery = new JPAQuery<>(entityManager);

        if(searchReqDto.getDealType().contains(SearchReqDto.DealType.DEAL)){
            jpaQuery = setBargainQuery(jpaQuery, searchReqDto);
            //jpaQuery.join(bargainDate.building, building);
        }
        if(searchReqDto.getDealType().contains(SearchReqDto.DealType.LEASE)){
            jpaQuery = setChaterQuery(jpaQuery, searchReqDto);
            //jpaQuery.join(charterDate.building, building);
        }
        if(searchReqDto.getDealType().contains(SearchReqDto.DealType.MONTH)){
            jpaQuery = setRentQuery(jpaQuery, searchReqDto);
            //jpaQuery.join(rentDate.building, building);
        }

        jpaQuery = setQueryHousingType(jpaQuery, searchReqDto.getHousingType());
        return jpaQuery.fetch();*/
//
//        jpaQuery = setBargainQuery(jpaQuery, searchReqDto);
//        jpaQuery.join(bargainDate.building, building);
//        jpaQuery = setQueryHousingType(jpaQuery, searchReqDto.getHousingType());
//
//        jpaQuery = setLocation(jpaQuery, searchReqDto);
//
//
//
//        return jpaQuery.fetch();
//    }

//    private JPAQuery<SearchResDto> setLocation(JPAQuery<SearchResDto> jpaQuery, SearchReqDto searchReqDto){
//        jpaQuery.select()
//                .from(building)
//                .where(building.latitude.between(searchReqDto.getMapLocation().getRightTop().getLatitude(), searchReqDto.getMapLocation().getLeftBottom().getLatitude()))
//                .where(building.longitude.between(searchReqDto.getMapLocation().getRightTop().getLongitude(), searchReqDto.getMapLocation().getLeftBottom().getLongitude()));
//        return jpaQuery;
//    }
//

    @Override
    public List<SearchResDto> getDealBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto){
        JPAQuery<SearchResDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery, searchReqDto);
        jpaQuery.join(bargainDate.building, building);
        jpaQuery = setQueryHousingType(jpaQuery, searchReqDto.getHousingType());

        return jpaQuery.fetch();
    }

    private JPAQuery<SearchResDto> setQuery(JPAQuery<SearchResDto> jpaQuery, SearchReqDto searchReqDto){
        jpaQuery.select(Projections.constructor(SearchResDto.class, building.city, building.groop, building.dong, building.name, building.area, building.floor, building.type, building.buildingNum,building.constructYear, bargainDate.price, building.latitude, building.longitude))
                .from(bargainDate)
                .where(building.latitude.between(searchReqDto.getMapLocation().getRightTop().getLatitude(), searchReqDto.getMapLocation().getLeftBottom().getLatitude()))
                .where(building.longitude.between(searchReqDto.getMapLocation().getRightTop().getLongitude(), searchReqDto.getMapLocation().getLeftBottom().getLongitude()));
        return jpaQuery;
    }

    private JPAQuery<SearchResDto> setQueryHousingType(JPAQuery<SearchResDto> jpaQuery, List<SearchReqDto.HousingType> housingType) {
        // housing type의 따라 동적 쿼리 생성
        if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.OPISTEL) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            return jpaQuery;
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.OPISTEL)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()).or(building.type.eq(SearchReqDto.HousingType.OPISTEL.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.OPISTEL) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.OPISTEL.name()).or(building.type.eq(SearchReqDto.HousingType.HOUSE.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()).or(building.type.eq(SearchReqDto.HousingType.HOUSE.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()));
        }
        else if(housingType.contains(SearchReqDto.HousingType.OPISTEL)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.OPISTEL.name()));
        }
        else if(housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.HOUSE.name()));
        }
        return jpaQuery;
    }
    /*private JPAQuery<SearchResDto> setBargainQuery(JPAQuery<SearchResDto> jpaQuery, SearchReqDto searchReqDto){
        //String deal = "bargain";
        //com.querydsl.core.types.Expression<String> deal = new com.querydsl.core.types.Expression<String>("bargain");
        jpaQuery.select(new QSearchResDto(building.city, building.groop, building.dong, building.name, building.area, building.floor, building.type, building.buildingNum,building.constructYear, bargainDate.price, Expressions.constant("bargain"), bargainDate.date, building.latitude, building.longitude))
                .from(bargainDate, building)
                .where(building.latitude.between(searchReqDto.getMapLocation().getRightTop().getLatitude(), searchReqDto.getMapLocation().getLeftBottom().getLatitude()))
                .where(building.longitude.between(searchReqDto.getMapLocation().getRightTop().getLongitude(), searchReqDto.getMapLocation().getLeftBottom().getLongitude()));
        return jpaQuery;
    }

    private JPAQuery<SearchResDto> setChaterQuery(JPAQuery<SearchResDto> jpaQuery, SearchReqDto searchReqDto){
        jpaQuery.select(new QSearchResDto(building.city, building.groop, building.dong, building.name, building.area, building.floor, building.type, building.buildingNum,building.constructYear, charterDate.price, Expressions.constant("charter"),charterDate.date,building.latitude, building.longitude))
                .from(charterDate, building)
                .where(building.latitude.between(searchReqDto.getMapLocation().getRightTop().getLatitude(), searchReqDto.getMapLocation().getLeftBottom().getLatitude()))
                .where(building.longitude.between(searchReqDto.getMapLocation().getRightTop().getLongitude(), searchReqDto.getMapLocation().getLeftBottom().getLongitude()));
        return jpaQuery;
    }

    private JPAQuery<SearchResDto> setRentQuery(JPAQuery<SearchResDto> jpaQuery, SearchReqDto searchReqDto){
        jpaQuery.select(new QSearchResDto(building.city, building.groop, building.dong, building.name, building.area, building.floor, building.type, building.buildingNum,building.constructYear, rentDate.monthlyPrice, rentDate.guaranteePrice,Expressions.constant("rent"),rentDate.date ,building.latitude, building.longitude))
                .from(rentDate, building)
                .where(building.latitude.between(searchReqDto.getMapLocation().getRightTop().getLatitude(), searchReqDto.getMapLocation().getLeftBottom().getLatitude()))
                .where(building.longitude.between(searchReqDto.getMapLocation().getRightTop().getLongitude(), searchReqDto.getMapLocation().getLeftBottom().getLongitude()));
        return jpaQuery;
    }

    private JPAQuery<SearchResDto> setQueryHousingType(JPAQuery<SearchResDto> jpaQuery, List<SearchReqDto.HousingType> housingType) {
        // housing type의 따라 동적 쿼리 생성
        if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.OPISTEL) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            return jpaQuery;
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.OPISTEL)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()).or(building.type.eq(SearchReqDto.HousingType.OPISTEL.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.OPISTEL) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.OPISTEL.name()).or(building.type.eq(SearchReqDto.HousingType.HOUSE.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()).or(building.type.eq(SearchReqDto.HousingType.HOUSE.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()));
        }
        else if(housingType.contains(SearchReqDto.HousingType.OPISTEL)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.OPISTEL.name()));
        }
        else if(housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.HOUSE.name()));
        }
        return jpaQuery;
    }
*/
}