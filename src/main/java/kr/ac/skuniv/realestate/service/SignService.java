package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.MemberRole;
import kr.ac.skuniv.realestate.domain.dto.SignInDto;
import kr.ac.skuniv.realestate.domain.dto.SignupDto;
import kr.ac.skuniv.realestate.domain.entity.Member;
import kr.ac.skuniv.realestate.exception.UserDefineException;
import kr.ac.skuniv.realestate.repository.SignRepository;
import kr.ac.skuniv.realestate.security.TokenUtils;
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
/*
    public SignService(SignRepository signRepository, PasswordEncoder passwordEncoder) {
        this.signRepository = signRepository;
        this.passwordEncoder = passwordEncoder;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = signRepository.findByEmail(username)
                .orElseThrow(() -> new UserDefineException(username + "은 없는 사용자입니다."));
        return new User(member.getEmail(), member.getPassword(), authorities(member.getRoles()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<MemberRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE" + r.name()))
                .collect(Collectors.toSet());
    }

    public String saveMember(SignupDto signupDto, String who){
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Member member;
        if(who.equals(MemberRole.ADMIN.name()))
            member = signRepository.save(
                    signupDto.toEntity(
                            Stream.of(MemberRole.ADMIN, MemberRole.USER).collect(Collectors.toSet())
                    )
            );
        else
            member = signRepository.save(
                    signupDto.toEntity(
                            Stream.of(MemberRole.USER).collect(Collectors.toSet())
                    )
            );

        return TokenUtils.createToken(member.toUser());
    }

    public String signInMember(SignInDto signInDto){
        User user = (User) loadUserByUsername(signInDto.getEmail());
        if(passwordEncoder.matches(signInDto.getPassword(), user.getPassword()))
            return TokenUtils.createToken(user);
        else
            throw new UserDefineException("비밀번호가 잘못되었습니다.");

    }

    public void updateMember(SignupDto signupDto){
        Member member = signRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(member != null)
            signRepository.save(member);
    }

    public void deleteMember(SignInDto signInDto){
        Member member = signRepository.findByEmail(signInDto.getEmail()).orElse(null);
        if(member != null)
            signRepository.delete(member);
    }
}
