package kr.ac.skuniv.realestate.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegionDto {

    private int cityCode;
    private int groopCode;
    private String dongName;
    private RegionType regionType;
    private String code;
    private Boolean hasDong;

//    public RegionDto(int cityCode, RegionType regionType) {
//        this.cityCode = cityCode;
//        this.regionType = regionType;
//    }
//
//    public RegionDto(int cityCode, int groopCode, RegionType regionType) {
//        this.cityCode = cityCode;
//        this.groopCode = groopCode;
//        this.regionType = regionType;
//    }

//    @Builder
//    public RegionDto(String cityCode, String groopCode, String dongName, RegionType regionType) {
//        this.cityCode = cityCode;
//        this.groopCode = groopCode;
//        this.dongName = dongName;
//        this.regionType = regionType;
//    }

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
