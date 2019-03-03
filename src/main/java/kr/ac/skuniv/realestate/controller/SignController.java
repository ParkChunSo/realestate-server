package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.MemberRole;
import kr.ac.skuniv.realestate.domain.dto.SignInDto;
import kr.ac.skuniv.realestate.domain.dto.SignupDto;
import kr.ac.skuniv.realestate.service.SignService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realestate/sign")
public class SignController {
    private final SignService signService;

    public SignController(SignService signService) {
        this.signService = signService;
    }

    @ApiOperation("일반 사용자 회원가입")
    @PostMapping("/client")
    public String clientSignUp(@RequestBody SignupDto signUpDto) {
        return signService.saveMember(signUpDto, MemberRole.USER.name());
    }

    @ApiOperation("관리자 회원가입")
    @PostMapping("/admin")
    public String adminSignUp(@RequestBody SignupDto signUpDto) {
        return signService.saveMember(signUpDto, MemberRole.ADMIN.name());
    }

    @ApiOperation("로그인")
    @PostMapping
    public String signIn(@RequestBody SignInDto signInDto) {
        return signService.signInMember(signInDto);
    }

    @ApiOperation("사용자 정보 수정")
    @PutMapping
    public void updateMember(@RequestBody SignupDto signupDto) {
        signService.updateMember(signupDto);
    }

    @ApiOperation("사용자 정보 삭제")
    @DeleteMapping
    public void deleteMember(@RequestBody SignInDto signInDto) {
        signService.deleteMember(signInDto);
    }
}
