package kr.ac.skuniv.realestate.security;

import com.google.common.collect.ImmutableList;
import kr.ac.skuniv.realestate.domain.MemberRole;
import kr.ac.skuniv.realestate.service.SignService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .cors().and()
                .authorizeRequests()
                    .mvcMatchers("/realestate/sign").authenticated()
                    .mvcMatchers("/realestate/sign/admin").hasRole(MemberRole.ADMIN.name())
                    .mvcMatchers(HttpMethod.GET, "/realestate/board/**").authenticated()
                    .anyRequest().anonymous()
                .and()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPointCustom).accessDeniedHandler(deniedHandlerCustom)
                .and()
                    .addFilterBefore(authenticationTokenProcessingFilter, BasicAuthenticationFilter.class)
                    .logout().logoutUrl("/realestate/sign/logout").logoutSuccessHandler(logoutSuccessHandlerCustom).and()

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("X-Auth-Token","Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}