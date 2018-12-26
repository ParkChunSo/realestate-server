package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

@Data
public class RegionDto {
    private String cityCode;
    private String groopCode;
    private String dongName;
    private String regionStatus;

    public RegionDto(String cityCode, String regionStatus) {
        this.cityCode = cityCode;
        this.regionStatus = regionStatus;
    }

    public RegionDto(String cityCode, String groopCode, String regionStatus) {
        this.cityCode = cityCode;
        this.groopCode = groopCode;
        this.regionStatus = regionStatus;
    }

    public RegionDto(String cityCode, String groopCode, String dongName, String regionStatus) {
        this.cityCode = cityCode;
        this.groopCode = groopCode;
        this.dongName = dongName;
        this.regionStatus = regionStatus;
    }

    @Override
    public String toString() {
        return "RegionDto{" +
                "cityCode='" + cityCode + '\'' +
                ", groopCode='" + groopCode + '\'' +
                ", dongName='" + dongName + '\'' +
                ", regionStatus='" + regionStatus + '\'' +
                '}';
    }
}
