package com.enjoytrip.jwt;

import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.domain.model.type.Role;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            Long id = Long.parseLong(jwtProvider.getUserIdFromToken(token));
            String nickname = jwtProvider.getNicknameFromToken(token);
            List<GrantedAuthority> authorities = jwtProvider.getAuthorities(token);
            String authority = authorities.get(0).getAuthority().substring(5);
            Member member = new Member(id, "", "", "", nickname, Role.valueOf(authority), false);
            Authentication authentication = new UsernamePasswordAuthenticationToken(member, token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
