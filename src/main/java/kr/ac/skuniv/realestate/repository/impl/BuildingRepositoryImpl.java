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

@Component
public class BuildingRepositoryImpl extends QuerydslRepositorySupport implements BuildingRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    private QBuilding building = QBuilding.building;
    private QBargainDate bargainDate = QBargainDate.bargainDate;
    private QCharterDate charterDate = QCharterDate.charterDate;
    private QRentDate rentDate = QRentDate.rentDate;

    public BuildingRepositoryImpl() {
        super(Building.class);
    }

    @Override
    public List<SearchResDto> getDealBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto){
        JPAQuery<SearchResDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery, searchReqDto);
        jpaQuery.leftJoin(building.bargainDates, bargainDate);
//        jpaQuery = setQueryHousingType(jpaQuery, searchReqDto.getHousingType());

        return jpaQuery.fetch();
    }

    @Override
    public List<SearchResDto> getLeaseBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto) {
        return null;
    }

    @Override
    public List<SearchResDto> getRentBuildingsByMapXYAndHousingType(SearchReqDto searchReqDto) {
        return null;
    }

    private JPAQuery<SearchResDto> setQuery(JPAQuery<SearchResDto> jpaQuery, SearchReqDto searchReqDto){
        jpaQuery.select(Projections.constructor(SearchResDto.class, building.city, building.groop, building.dong, building.name, building.area, building.floor, building.type, building.buildingNum,building.constructYear, bargainDate.price, building.latitude, building.longitude))
                .from(building)
                .where(building.latitude.between(searchReqDto.getMapLocation().getLeftBottom().getLatitude(), searchReqDto.getMapLocation().getRightTop().getLatitude()))
                .where(building.longitude.between(searchReqDto.getMapLocation().getLeftBottom().getLongitude(), searchReqDto.getMapLocation().getRightTop().getLongitude()));
        return jpaQuery;
    }

    private JPAQuery<SearchResDto> setQueryHousingType(JPAQuery<SearchResDto> jpaQuery, List<SearchReqDto.HousingType> housingType) {
        // 모든 housing타입을 원함.
        if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.OFFICETEL) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            return jpaQuery;
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.OFFICETEL)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()).or(building.type.eq(SearchReqDto.HousingType.OFFICETEL.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.OFFICETEL) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.OFFICETEL.name()).or(building.type.eq(SearchReqDto.HousingType.HOUSE.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART) && housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()).or(building.type.eq(SearchReqDto.HousingType.HOUSE.name())));
        }
        else if(housingType.contains(SearchReqDto.HousingType.APART)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.APART.name()));
        }
        else if(housingType.contains(SearchReqDto.HousingType.OFFICETEL)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.OFFICETEL.name()));
        }
        else if(housingType.contains(SearchReqDto.HousingType.HOUSE)){
            jpaQuery.where(building.type.eq(SearchReqDto.HousingType.HOUSE.name()));
        }
        return jpaQuery;
    }

    private JPAQuery<SearchResDto> setQueryDealType(JPAQuery<SearchResDto> jpaQuery, List<SearchReqDto.DealType> dealType) {
        if(dealType.contains(SearchReqDto.DealType.DEAL)){
            jpaQuery.join(building.bargainDates, bargainDate);
        }
        if(dealType.contains(SearchReqDto.DealType.LEASE)){
            jpaQuery.join(building.charterDates, charterDate);
        }
        if(dealType.contains(SearchReqDto.DealType.MONTH)){
            jpaQuery.join(building.rentDates, rentDate);
        }
        return jpaQuery;
    }

}
