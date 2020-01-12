package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.dto.loginDto.SignupDto;
import kr.ac.skuniv.realestate.domain.entity.Member;
import kr.ac.skuniv.realestate.domain.enums.MemberRole;
import kr.ac.skuniv.realestate.exception.UserDefineException;
import kr.ac.skuniv.realestate.jwtConfig.JwtProvider;
import kr.ac.skuniv.realestate.repository.SignRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SignService{

    private final SignRepository signRepository;
    private final PasswordEncoder passwordEncoder;

    public SignService(SignRepository signRepository, PasswordEncoder passwordEncoder) {
        this.signRepository = signRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 로그인
     *
     * @param username: 사용자 아이디
     * @param userPassword: 사용자 패스워드
     * @return JWT 발급
     */
    public String signInMember(String username, String userPassword) throws UserDefineException{
        Member member = signRepository.findByEmail(username).orElseThrow(() -> new UserDefineException("User not found"));
        if(passwordEncoder.matches(userPassword, member.getPassword()))
            return JwtProvider.createToken(member.getEmail(), member.getRoles());
        else
            throw new UserDefineException("Password not correct");
    }

    /**
     * 회원가입
     *
     * @param signupDto: 사용자의 아이디, 패스워드, 이름
     * @param who: 권한
     * @return 사용자 정보
     */
    public Member saveMember(SignupDto signupDto, String who){
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        if(signRepository.findByEmail(signupDto.getEmail()).isPresent())
            throw new UserDefineException("이미 존재하는 회원입니다.");

        if(who.equals(MemberRole.ADMIN.name()))
            return signRepository.save(
                    signupDto.toEntity(
                            Stream.of(MemberRole.ADMIN, MemberRole.USER).collect(Collectors.toSet())
                    )
            );
        else {
            return signRepository.save(
                    signupDto.toEntity(
                            Stream.of(MemberRole.USER).collect(Collectors.toSet())
                    )
            );
        }
    }
}
