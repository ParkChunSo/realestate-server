package kr.ac.skuniv.realestate.predicate;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.ac.skuniv.realestate.domain.entity.QBuilding;

public class BuildingPredicate {

    public static Predicate search(Long buildingNo, String city) {
        QBuilding building = QBuilding.building;

        BooleanBuilder builder = new BooleanBuilder();
        if(buildingNo != null) {
            builder.and(building.buildingNo.eq(buildingNo));
        }
        if(city != null) {
            builder.and(building.city.eq(city));
        }
        return builder;
    }

}
