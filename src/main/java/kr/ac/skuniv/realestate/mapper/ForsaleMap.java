package kr.ac.skuniv.realestate.mapper;

import kr.ac.skuniv.realestate.domain.dto.GraphDto;
import kr.ac.skuniv.realestate.domain.entity.Forsale;
import org.modelmapper.PropertyMap;

public class ForsaleMap extends PropertyMap<Forsale, GraphDto> {
    @Override
    protected void configure() {
//        map().setDealType(source.getDealType());
////        map().setHousingType(source.getHousingType());
////        skip().setAverage(null);
    }
}
