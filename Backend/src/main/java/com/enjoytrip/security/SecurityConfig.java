package com.enjoytrip.security;

import com.enjoytrip.jwt.JwtAccessDeniedHandler;
import com.enjoytrip.jwt.JwtAuthenticationEntryPoint;
import com.enjoytrip.jwt.JwtAuthenticationFilter;
import com.enjoytrip.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    //      jwtAtuthenticationEntryPoint는 유효한 자격증명을 제공하지 않고 접근하려할 때 401에러를 리턴하는 클래스이다.
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //     jwtAccessDeniedHandler는 필요한 권한이 존재하지 않을 경우 403 forbidden 에러를 리턴하는 클래스이다.
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    // 비밀번호 암호화
    //JWT를 사용하기 위해서는 기본적으로 password encoder가 필요한데, 여기서는 Bycrypt encoder를 사용했다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()//무시한다.
                .antMatchers("/h2-console/**", "/favicon.ico"// `/h2-console/**` 과 `/favicon.ico` 하위 모든 요청과 파비콘은 인증을 무시한다.
                        , "/error", "/static/js/**", "/static/css/**", "/static/img/**", "/static/frontend/**");

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));       // 모든 출처에서 오는 요청 허용
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));     // 허용할 HTTP 메소드
        configuration.setAllowedHeaders(Arrays.asList(      // 요청 헤더 중 허용할 헤더 설정
                "Authorization",
                "Cache-Control",
                "Content-Type",
                "X-AUTH-TOKEN"
        ));
        configuration.setAllowCredentials(true);    // 인증 정보(cookies, headers) 등을 포함한 요청을 허용하도록 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();     // URL 기반의 CORS 설정 소스 객체 생성
        source.registerCorsConfiguration("/**", configuration);     // 모든 url 패턴에 대해 CORS 설정 적용
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // .httpBasic().disable() // rest api 만을 고려하여 기본설정 해제
                .csrf().disable()//csrf 보안을 사용하지 않겠다

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 토큰 기반 인증이므로 세션 사용 안함
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))


                //요청에 대한 사용 권한 체크
                .authorizeRequests() // 요청에 대한 사용 권한 체크
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/member/info").hasAnyRole("USER", "ADMIN")
                .antMatchers("/trip/new").hasRole("USER")
                .antMatchers("/auth/user").hasRole("USER")
//                .antMatchers("/api/**").permitAll()
//                .anyRequest().authenticated()
                .anyRequest().permitAll()

                // 그외 나머지 요청은 누구나 접근 가능
//                .and().logout()
                .and().addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}