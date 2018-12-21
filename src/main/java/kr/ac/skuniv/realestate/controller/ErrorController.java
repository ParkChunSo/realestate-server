package kr.ac.skuniv.realestate.controller;

import kr.ac.skuniv.realestate.exception.UserDefineException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "realestate/condition/*")
public class ErrorController {

    @GetMapping({"/city/date","/city/date/{date}","/city/district/{district}/date","/city/district/{district}/date/{date}"})
    public void noCity(){
        throw new UserDefineException("city파라미터가 없습니다");
    }

    @GetMapping({"/city/{city}/district/date","/city/{city}/district/date/{date}"})
    public void noDistrict(){
        throw new UserDefineException("district파라미터가 없습니다");
    }

    @GetMapping({"/city/{city}/district/{district}/neighborhood/date","/city/{city}/district/{district}/neighborhood/date/{date}"})
    public void noNeighborhood(){
        throw new UserDefineException("district파라미터가 없습니다");
    }

    /*@GetMapping({"/city/date","/city/date/{date}","/city/district/{district}/date","/city/district/{district}/date/{date}"})
    public void noCityAndDistrict(){
        throw new UserDefineException("city파라미터가 없습니다");
    }

    @GetMapping({"/city/date","/city/date/{date}","/city/district/{district}/date","/city/district/{district}/date/{date}"})
    public void noCity(){
        throw new UserDefineException("city파라미터가 없습니다");
    }*/
}
