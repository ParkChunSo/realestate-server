package kr.ac.skuniv.realestate.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionDto {

    private String cityCode;
    private String groopCode;
    private String dongName;
    private RegionType regionType;

    public RegionDto(String cityCode, RegionType regionType) {
        this.cityCode = cityCode;
        this.regionType = regionType;
    }

    public RegionDto(String cityCode, String groopCode, RegionType regionType) {
        this.cityCode = cityCode;
        this.groopCode = groopCode;
        this.regionType = regionType;
    }

    @Builder
    public RegionDto(String cityCode, String groopCode, String dongName, RegionType regionType) {
        this.cityCode = cityCode;
        this.groopCode = groopCode;
        this.dongName = dongName;
        this.regionType = regionType;
    }

    public enum RegionType {
        CITY("대코드"), DISTRICT("중코드"), NEIGHBORHOOD("소코드");

        private String explain;

        RegionType(String explain) {
            this.explain = explain;
        }

        public String getExplain() {
            return explain;
        }
    }
}
