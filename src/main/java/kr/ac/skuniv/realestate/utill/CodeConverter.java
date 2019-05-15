package kr.ac.skuniv.realestate.utill;

import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.domain.entity.RegionCode;
import kr.ac.skuniv.realestate.repository.RegionCodeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CodeConverter {

    private static RegionCodeRepository regionCodeRepository;

    public static RegionDto getCityCode(String city) {
        RegionCode regionCode = regionCodeRepository.findById(city).get();
        return RegionDto.builder()
                .code(regionCode.getValue().substring(0,2))
                .hasDong(false)
                .build();
    }

    public static RegionDto getCityAndGroopCode(String city, String groop) {
        RegionCode regionCode = regionCodeRepository.findById(city + groop).get();
        return RegionDto.builder()
                .code(regionCode.getValue().substring(0,5))
                .hasDong(false)
                .build();
    }

    public static RegionDto getAllCode(String city, String groop, String dong) {
        RegionCode regionCode = regionCodeRepository.findById(city + groop + dong).get();
        return RegionDto.builder()
                .code(regionCode.getValue().substring(0,5))
                .dongName(dong)
                .hasDong(true)
                .build();
    }

}
