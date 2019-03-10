package kr.ac.skuniv.realestate.security;

import kr.ac.skuniv.realestate.domain.MemberRole;
import kr.ac.skuniv.realestate.service.SignService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SignService signService;
    private final AuthenticationEntryPointCustom authenticationEntryPointCustom;
    private final AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;
    private final AccessDeniedHandlerCustom deniedHandlerCustom;
    private final LogoutSuccessHandlerCustom logoutSuccessHandlerCustom;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(SignService signService, AuthenticationEntryPointCustom authenticationEntryPointCustom, AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter, AccessDeniedHandlerCustom deniedHandlerCustom, LogoutSuccessHandlerCustom logoutSuccessHandlerCustom, PasswordEncoder passwordEncoder) {
        this.signService = signService;
        this.authenticationEntryPointCustom = authenticationEntryPointCustom;
        this.authenticationTokenProcessingFilter = authenticationTokenProcessingFilter;
        this.deniedHandlerCustom = deniedHandlerCustom;
        this.logoutSuccessHandlerCustom = logoutSuccessHandlerCustom;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                    .mvcMatchers("/realestate/sign").authenticated()
                    .mvcMatchers("/realestate/sign/admin").hasRole(MemberRole.ADMIN.name())
                    .mvcMatchers(HttpMethod.GET, "/realestate/board/**").authenticated()
                    .anyRequest().anonymous()
                .and()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPointCustom).accessDeniedHandler(deniedHandlerCustom)
                .and()
                    .addFilterBefore(authenticationTokenProcessingFilter, BasicAuthenticationFilter.class)
                    .logout().logoutUrl("/realestate/sign/logout").logoutSuccessHandler(logoutSuccessHandlerCustom)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/swagger-ui.html")
                .mvcMatchers(HttpMethod.POST,"/realestate/sign")
                .mvcMatchers("/realestate/sign/client")
                .mvcMatchers(HttpMethod.GET, "/realestate/board/**")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(signService)
            .passwordEncoder(passwordEncoder);
    }
}