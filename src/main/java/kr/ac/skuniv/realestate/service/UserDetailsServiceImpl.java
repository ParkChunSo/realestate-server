package kr.ac.skuniv.realestate.service;

import kr.ac.skuniv.realestate.domain.enums.MemberRole;
import kr.ac.skuniv.realestate.domain.entity.Member;
import kr.ac.skuniv.realestate.exception.UserDefineException;
import kr.ac.skuniv.realestate.repository.SignRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    public UserDetailsServiceImpl(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    private final SignRepository signRepository;

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
}
