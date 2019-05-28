package kr.ac.skuniv.realestate.utill;

import kr.ac.skuniv.realestate.domain.dto.RegionDto;
import kr.ac.skuniv.realestate.domain.entity.RegionCode;
import kr.ac.skuniv.realestate.repository.RegionCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@RequiredArgsConstructor
@Component
public class RegionCodeConverter {

    private static RegionCodeRepository regionCodeRepository;

    @Autowired
    private RegionCodeRepository regionCodeRepository0;

    @PostConstruct
    private void init () {
        RegionCodeConverter.regionCodeRepository = regionCodeRepository0;
    }

    public static RegionDto getCityCode(String city) {
        //RegionCode regionCode = regionCodeRepository.findById(city).get();
        String code = regionCodeRepository.findById(city).get().getValue();
        return RegionDto.builder()
                .cityCode(Integer.valueOf(code.substring(0,2)))
                .regionType(RegionDto.RegionType.CITY)
                .build();
    }

    public static RegionDto getCityAndGroopCode(String city, String groop) {
//        RegionCode regionCode = regionCodeRepository.findById(city + groop).get();
        String code = regionCodeRepository.findById(city + groop).get().getValue();
        return RegionDto.builder()
                .cityCode(Integer.valueOf(code.substring(0,2)))
                .groopCode(Integer.valueOf(code.substring(2,5)))
                .regionType(RegionDto.RegionType.DISTRICT)
                .build();
    }

    public static RegionDto getAllCode(String city, String groop, String dong) {
//        RegionCode regionCode = regionCodeRepository.findById(city + groop + dong).get();
        String code = regionCodeRepository.findById(city + groop + dong).get().getValue();
        return RegionDto.builder()
                .cityCode(Integer.valueOf(code.substring(0,2)))
                .groopCode(Integer.valueOf(code.substring(2,5)))
                .dongName(dong)
                .regionType(RegionDto.RegionType.NEIGHBORHOOD)
                .build();
    }
}
