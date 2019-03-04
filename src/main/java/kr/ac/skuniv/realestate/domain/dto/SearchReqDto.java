package kr.ac.skuniv.realestate.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SearchReqDto {
    private List<DealType> dealType;

    private List<HousingType> housingType;

    private MapLocationDto mapLocation;

    private List<LocationDto> options;

    public enum DealType {
        LEASE, DEAL, MONTH
    }

    public enum HousingType{
        APART, OPISTEL, HOUSE
    }
}
