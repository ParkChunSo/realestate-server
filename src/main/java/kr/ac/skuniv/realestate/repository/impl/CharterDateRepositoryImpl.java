package kr.ac.skuniv.realestate.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.realestate.aop.AspectExceptionAnnotation;
import kr.ac.skuniv.realestate.domain.dto.DateDto;
import kr.ac.skuniv.realestate.domain.dto.GraphTmpDto;
import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.domain.entity.*;
import kr.ac.skuniv.realestate.repository.custom.CharterDateRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by youngman on 2019-01-16.
 */

@SuppressWarnings("Duplicates")
@Component
public class CharterDateRepositoryImpl extends QuerydslRepositorySupport implements CharterDateRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public CharterDateRepositoryImpl() {
        super(CharterDate.class);
    }

    @Override
    @AspectExceptionAnnotation
    public List<GraphTmpDto> getByRegionDtoAndDateDto(RegionDto regionDto, DateDto dateDto) {
        QBuilding building = QBuilding.building;
        QCharterDate charterDate = QCharterDate.charterDate;

        JPAQuery<GraphTmpDto> query = new JPAQuery<>(entityManager);
        query.select(Projections.constructor(GraphTmpDto.class, building.type, charterDate.date, charterDate.price.avg()))
                .from(charterDate)
                .join(charterDate.building, building);

        if (regionDto.getRegionType() == RegionDto.RegionType.CITY) {
            query.where(building.city.eq(regionDto.getCityCode()));
        } else if (regionDto.getRegionType() == RegionDto.RegionType.DISTRICT) {
            query.where(building.city.eq(regionDto.getCityCode()), building.groop.eq(regionDto.getGroopCode()));
        } else if (regionDto.getRegionType() == RegionDto.RegionType.NEIGHBORHOOD){
            query.where(building.city.eq(regionDto.getCityCode()), building.groop.eq(regionDto.getGroopCode()), building.dong.eq(regionDto.getDongName()));
        }

        if (dateDto.getDateType() == DateDto.DateType.YEAR) {
            query.groupBy(building.type, charterDate.date.year());
        } else if (dateDto.getDateType() == DateDto.DateType.MONTH) {
            query.where(charterDate.date.year().eq(dateDto.getLocalDate().getYear()))
                    .groupBy(building.type, charterDate.date.month());
        } else if (dateDto.getDateType() == DateDto.DateType.DAY) {
            query.where(charterDate.date.year().eq(dateDto.getLocalDate().getYear()), charterDate.date.month().eq(dateDto.getLocalDate().getMonthValue()))
                    .groupBy(building.type, charterDate.date);
        }

        return query.fetch();
    }
}
