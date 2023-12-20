package com.enjoytrip.security;

import com.enjoytrip.jwt.JwtAccessDeniedHandler;
import com.enjoytrip.jwt.JwtAuthenticationEntryPoint;
import com.enjoytrip.jwt.JwtAuthenticationFilter;
import com.enjoytrip.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtProvider jwtProvider;
//    private final MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService(memberService);
//        return authenticationProvider;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .authenticationProvider(daoAuthenticationProvider())
.csrf().disable()
.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
.and()
.authorizeRequests(
        registry -> registry.antMatchers("/admin/**").hasRole("ADMIN")
                            .antMatchers("/board/**").hasRole("USER")
                            .antMatchers("/member/info").hasAnyRole("USER", "ADMIN")
                            .antMatchers("/member/signup/**").permitAll()
                            .anyRequest().authenticated()
)
.formLogin(
        configurer -> configurer.successHandler(new FormLoginAuthenticationSuccessHandler(jwtProvider))
                                .failureHandler(new FormLoginAuthenticationFailureHandler())
)
.exceptionHandling(
        configurer -> configurer.accessDeniedHandler(new JwtAccessDeniedHandler())
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
)
.addFilterBefore(
        new JwtAuthenticationFilter(jwtProvider),
        UsernamePasswordAuthenticationFilter.class
);

        return http.build();
    }
}
