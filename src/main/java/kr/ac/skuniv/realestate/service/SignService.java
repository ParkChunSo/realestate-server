package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.MemberRole;
import kr.ac.skuniv.realestate.domain.dto.loginDto.SignupDto;
import kr.ac.skuniv.realestate.domain.entity.Member;
import kr.ac.skuniv.realestate.exception.UserDefineException;
import kr.ac.skuniv.realestate.repository.SignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SignService implements UserDetailsService {

    @Autowired
    private SignRepository signRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = signRepository.findByEmail(username)
                .orElseThrow(() -> new UserDefineException("아이디를 잘못 입력하셨습니다."));
        return new User(member.getEmail(), member.getPassword(), authorities(member.getRoles()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<MemberRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE" + r.name()))
                .collect(Collectors.toSet());
    }

//    public String signInMember(SignInDto signInDto){
//        User user = (User) loadUserByUsername(signInDto.getEmail());
//        if(passwordEncoder.matches(signInDto.getPassword(), user.getPassword()))
//            return TokenUtils.createToken(user);
//        else
//            throw new UserDefineException("비밀번호가 잘못되었습니다.");
//    }

    public void saveMember(SignupDto signupDto, String who){
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        if(signRepository.findByEmail(signupDto.getEmail()).isPresent())
            throw new UserDefineException("이미 존재하는 회원입니다.");

//        Member member;
        if(who.equals(MemberRole.ADMIN.name()))
            signRepository.save(
                    signupDto.toEntity(
                            Stream.of(MemberRole.ADMIN, MemberRole.USER).collect(Collectors.toSet())
                    )
            );
        else
            signRepository.save(
                    signupDto.toEntity(
                            Stream.of(MemberRole.USER).collect(Collectors.toSet())
                    )
            );
    }
//
//    public void updateMember(SignupDto signupDto){
//        Member member = signRepository.findByEmail(signupDto.getEmail())
//                .orElseThrow(() -> new UserDefineException("아이디가 존재하지 않습니다."));
//        member.setName(signupDto.getName());
//        member.setEmail(signupDto.getEmail());
//        member.setEmail(passwordEncoder.encode(signupDto.getPassword()));
//        signRepository.save(member);
//    }
//
//    public void deleteMember(SignInDto signInDto){
//        Member member = signRepository.findByEmail(signInDto.getEmail())
//                .orElseThrow(() -> new UserDefineException("아이디를 잘못 입력하셨습니다."));
//        if(passwordEncoder.matches(signInDto.getPassword(), member.getPassword()))
//            signRepository.deleteById(member.getId());
//        else
//            throw new UserDefineException("비밀번호를 잘못 입력하셨습니다.");
//    }
}
