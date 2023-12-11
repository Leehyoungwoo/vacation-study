package com.enjoytrip.jwt;

import com.enjoytrip.security.CustomUserDetails;
import io.jsonwebtoken.*;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

@Component
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "authorities";

    private final String jwtHeaderKey;
    private final String secretKey;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public JwtProvider(
            @Value("${jwt.header}") String jwtHeaderKey,
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInSeconds
    ) {
        this.jwtHeaderKey = jwtHeaderKey;
        this.secretKey = secretKey;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("id", principal.getId())
                .claim("nickname", principal.getNickname())
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toCollection(ArrayList::new));
        CustomUserDetails principal = new CustomUserDetails(
                Long.parseLong(claims.get("id").toString()),
                claims.getSubject(),
                "",
                claims.get("nickname").toString(),
                authorities
        );

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String getNicknameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("Nickname")
                .toString();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("UserId")
                .toString();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String[] autorities = claims.get("authorites", String.class)
                .split(",");

        if (!StringUtils.hasText(autorities[0])) {
            return Collections.emptyList();
        }

        return Arrays.stream(autorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(jwtHeaderKey);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 비어있습니다.");
        }
        return false;
    }

}
