package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.dto.loginDto.JwtRequest;
import kr.ac.skuniv.realestate.domain.dto.loginDto.JwtResponse;
import kr.ac.skuniv.realestate.domain.dto.loginDto.SignupDto;
import kr.ac.skuniv.realestate.domain.entity.Member;
import kr.ac.skuniv.realestate.domain.enums.MemberRole;
import kr.ac.skuniv.realestate.service.SignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realestate/sign")
@CrossOrigin
public class SignController {
    private final SignService signService;

    public SignController(SignService signService) {
        this.signService = signService;
    }

    @ApiOperation("로그인")
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest){
        String token = signService.signInMember(jwtRequest.getUserId(), jwtRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @ApiOperation("일반 사용자 회원가입")
    @PostMapping("/client")
    public Member clientSignUp(@RequestBody SignupDto signUpDto) {
        return signService.saveMember(signUpDto, MemberRole.USER.name());
    }
}