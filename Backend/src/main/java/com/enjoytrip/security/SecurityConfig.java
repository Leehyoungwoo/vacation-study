package com.enjoytrip.security;

import com.enjoytrip.jwt.JwtAccessDeniedHandler;
import com.enjoytrip.jwt.JwtAuthenticationEntryPoint;
import com.enjoytrip.jwt.JwtAuthenticationFilter;
import com.enjoytrip.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
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
