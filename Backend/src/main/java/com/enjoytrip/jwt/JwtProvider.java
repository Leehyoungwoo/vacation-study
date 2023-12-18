package com.enjoytrip.jwt;

import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.domain.model.type.Role;
import com.enjoytrip.jwt.exception.MissingTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.enjoytrip.domain.exception.ExceptionMessage.TOKEN_NOT_FOUND;

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

        Member principal = (Member) authentication.getPrincipal();
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .claim(AUTHORITIES_KEY, authorities)
                .claim("id", principal.getId())
                .claim("nickname", principal.getNickname())
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toCollection(ArrayList::new));
        Long id = Long.parseLong(getUserIdFromToken(token).toString());
        String nickname = getNicknameFromToken(token).toString();
        String authority = getAuthorities(token).get(0).getAuthority();
        Member principal = new Member(id, "", "", "", nickname, Role.valueOf(authority), false);


        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String getNicknameFromToken(String token) {
        return parseClaims(token)
                .get("nickname")
                .toString();
    }

    public String getUserIdFromToken(String token) {
        return parseClaims(token)
                .get("id")
                .toString();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = parseClaims(token);

        String[] authorities = claims.get("authorities", String.class)
                .split(",");

        if (!StringUtils.hasText(authorities[0])) {
            return Collections.emptyList();
        }

        return Arrays.stream(authorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtHeaderKey);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        throw new MissingTokenException(TOKEN_NOT_FOUND);
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
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
