package kr.ac.skuniv.realestate.domain.entity;

import kr.ac.skuniv.realestate.domain.MemberRole;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity @Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roles;

    public User toUser(){
        return new User(email, password, roles.stream()
                                        .map(r -> new SimpleGrantedAuthority("ROLE" + r.name()))
                                        .collect(Collectors.toSet()));
    }
}
