package com.enjoytrip.security;

import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        final Member member = memberRepository.findByUsername(username)
                                              .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        return new CustomUserDetails(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getNickname(),
                AuthorityUtils.createAuthorityList("ROLE_" + member.getRole().name())
        );
    }
}
