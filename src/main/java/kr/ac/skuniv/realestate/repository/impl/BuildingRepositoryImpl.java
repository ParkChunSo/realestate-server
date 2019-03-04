package kr.ac.skuniv.realestate.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.realestate.domain.dto.SearchReqDto;
import kr.ac.skuniv.realestate.domain.dto.SearchResDto;
import kr.ac.skuniv.realestate.domain.dto.SearchTmpDto;
import kr.ac.skuniv.realestate.domain.entity.*;
import kr.ac.skuniv.realestate.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static kr.ac.skuniv.realestate.domain.entity.QBargainDate.bargainDate;
import static kr.ac.skuniv.realestate.domain.entity.QCharterDate.charterDate;
import static kr.ac.skuniv.realestate.domain.entity.QRentDate.rentDate;

@Component
public class BuildingRepositoryImpl extends QuerydslRepositorySupport implements BuildingRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    private QBuilding building = QBuilding.building;

    public BuildingRepositoryImpl() {
        super(Building.class);
    }

    @Override
    public List<SearchTmpDto> getDealBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto){
        JPAQuery<SearchTmpDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(SearchTmpDto.class, building.buildingNo, building.city, building.groop, building.dong, building.name, building.area, building.floor, building.type, building.buildingNum,building.constructYear, bargainDate.price, bargainDate.date, building.latitude, building.longitude));
        jpaQuery = setQuery(jpaQuery, searchReqDto);
        jpaQuery.leftJoin(building.bargainDates, bargainDate);
        jpaQuery = setQueryHousingType(jpaQuery, searchReqDto.getHousingType());

        return jpaQuery.fetch();
    }

    @Override
    public List<SearchTmpDto> getLeaseBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto) {
        JPAQuery<SearchTmpDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(SearchTmpDto.class, building.buildingNo, building.city, building.groop, building.dong, building.name, building.area, building.floor, building.type, building.buildingNum,building.constructYear, charterDate.price, charterDate.date, building.latitude, building.longitude));
        jpaQuery = setQuery(jpaQuery, searchReqDto);
        jpaQuery.leftJoin(building.charterDates, charterDate);
        jpaQuery = setQueryHousingType(jpaQuery, searchReqDto.getHousingType());

        return jpaQuery.fetch();
    }

    @Override
    public List<SearchTmpDto> getRentBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto) {
        JPAQuery<SearchTmpDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.bean(SearchTmpDto.class, building.buildingNo, building.city, building.groop, building.dong, building.name, building.area, building.floor, building.type, building.buildingNum,building.constructYear, rentDate.guaranteePrice, rentDate.monthlyPrice, rentDate.date, building.latitude, building.longitude));
        jpaQuery = setQuery(jpaQuery, searchReqDto);
        jpaQuery.leftJoin(building.rentDates, rentDate);
        jpaQuery = setQueryHousingType(jpaQuery, searchReqDto.getHousingType());

        return jpaQuery.fetch();
    }

    private JPAQuery<SearchTmpDto> setQuery(JPAQuery<SearchTmpDto> jpaQuery, SearchReqDto searchReqDto){
        jpaQuery
                .from(building)
                .where(building.latitude.between(searchReqDto.getMapLocation().getLeftBottom().getLatitude(), searchReqDto.getMapLocation().getRightTop().getLatitude()))
                .where(building.longitude.between(searchReqDto.getMapLocation().getLeftBottom().getLongitude(), searchReqDto.getMapLocation().getRightTop().getLongitude()));
        return jpaQuery;
    }

    private JPAQuery<SearchTmpDto> setQueryHousingType(JPAQuery<SearchTmpDto> jpaQuery, List<SearchReqDto.HousingType> housingType) {
        // 모든 housing타입을 원함.
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

    private JPAQuery<SearchTmpDto> setQueryDealType(JPAQuery<SearchTmpDto> jpaQuery, List<SearchReqDto.DealType> dealType) {
        if(dealType.contains(SearchReqDto.DealType.DEAL) && dealType.contains(SearchReqDto.DealType.LEASE) && dealType.contains(SearchReqDto.DealType.MONTH)){

            jpaQuery.leftJoin(building.bargainDates, bargainDate);
        }
        else if(dealType.contains(SearchReqDto.DealType.DEAL) && dealType.contains(SearchReqDto.DealType.LEASE)){

        }
        else if(dealType.contains(SearchReqDto.DealType.LEASE) && dealType.contains(SearchReqDto.DealType.MONTH)){

        }
        else if(dealType.contains(SearchReqDto.DealType.DEAL) && dealType.contains(SearchReqDto.DealType.MONTH)){

        }
        else if(dealType.contains(SearchReqDto.DealType.DEAL)){

        }
        else if(dealType.contains(SearchReqDto.DealType.LEASE)){
            jpaQuery.leftJoin(building.charterDates, charterDate);
        }
        else if(dealType.contains(SearchReqDto.DealType.MONTH)){
            jpaQuery.leftJoin(building.rentDates, rentDate);
        }

        return jpaQuery;
    }

}