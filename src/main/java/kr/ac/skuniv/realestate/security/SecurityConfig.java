package kr.ac.skuniv.realestate.security;

import kr.ac.skuniv.realestate.domain.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthenticationEntryPointCustom authenticationEntryPointCustom;
    @Autowired
    AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;
    @Autowired
    AccessDeniedHandlerCustom deniedHandlerCustom;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                    .mvcMatchers(HttpMethod.POST, "/realestate/sign").permitAll()
                    .mvcMatchers("/realestate/sign").authenticated()

                    .mvcMatchers("/realestate/sign/client").permitAll()
                    .mvcMatchers("/realestate/sign/admin").hasRole(MemberRole.ADMIN.name())

                    .mvcMatchers(HttpMethod.GET, "/realestate/board/**").permitAll()
                    .mvcMatchers("/realestate/board/**").authenticated()
                    .anyRequest().anonymous()
                .and()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPointCustom).accessDeniedHandler(deniedHandlerCustom)
                .and()
                    .addFilterBefore(authenticationTokenProcessingFilter, BasicAuthenticationFilter.class)
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/realestate/logout")).logoutSuccessHandler(customLogoutSuccessHandler)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/swagger-ui.html");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
