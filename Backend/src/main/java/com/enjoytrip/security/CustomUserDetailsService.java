package com.enjoytrip.security;

import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .map(user -> new User(user.getUsername(),
                        user.getPassword(),
                        AuthorityUtils.createAuthorityList("ROLE_" + user.getRole().name())))
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
    }
}
