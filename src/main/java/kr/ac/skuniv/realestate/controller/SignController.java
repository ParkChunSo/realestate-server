package kr.ac.skuniv.realestate.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.realestate.domain.MemberRole;
import kr.ac.skuniv.realestate.domain.dto.loginDto.JwtRequest;
import kr.ac.skuniv.realestate.domain.dto.loginDto.JwtResponse;
import kr.ac.skuniv.realestate.domain.dto.loginDto.SignupDto;
import kr.ac.skuniv.realestate.jwtConfig.JwtTokenUtil;
import kr.ac.skuniv.realestate.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realestate/sign")
@CrossOrigin
//@RequiredArgsConstructor
public class SignController {
    @Autowired
    private SignService signService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ApiOperation("로그인")
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        authenticate(jwtRequest.getUserId() , jwtRequest.getPassword());

        final UserDetails userDetails = signService.loadUserByUsername(jwtRequest.getUserId());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @ApiOperation("일반 사용자 회원가입")
    @PostMapping("/client")
    public void clientSignUp(@RequestBody SignupDto signUpDto) {
        signService.saveMember(signUpDto, MemberRole.USER.name());
    }

//    @ApiOperation("관리자 회원가입")
//    @PostMapping("/admin")
//    public void adminSignUp(@RequestBody SignupDto signUpDto) {
//        signService.saveMember(signUpDto, MemberRole.ADMIN.name());
//    }
//
//    @ApiOperation("로그인")
//    @PostMapping()
//    public String signIn(@RequestBody SignInDto signInDto) {
//        return signService.signInMember(signInDto);
//    }
//
//    @ApiOperation("사용자 정보 수정")
//    @PutMapping()
//    public void updateMember(@RequestBody SignupDto signupDto) {
//        signService.updateMember(signupDto);
//    }
//
//    @ApiOperation("사용자 정보 삭제")
//    @DeleteMapping()
//    public void deleteMember(@RequestBody SignInDto signInDto) {
//        signService.deleteMember(signInDto);
//    }
//
//    @Secured("USER")
//    @GetMapping("/test")
//    public String test(){
//        return "Success login";
//    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}