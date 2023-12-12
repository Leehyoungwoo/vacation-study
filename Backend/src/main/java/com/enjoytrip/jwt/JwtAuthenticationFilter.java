package com.enjoytrip.jwt;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoytrip.security.CustomUserDetails;
import com.enjoytrip.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);

        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
            Long userId = Long.parseLong(jwtProvider.getUserIdFromToken(token));
            String username = jwtProvider.getUsernameFromToken(token);
            String nickname = jwtProvider.getNicknameFromToken(token);
            List<GrantedAuthority> authorities = jwtProvider.getAuthorities(token);
            // CustomUserDetails를 로드하고 Authentication 객체 생성
            CustomUserDetails userDetails = new CustomUserDetails(userId, username, "", nickname, authorities);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, token, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
