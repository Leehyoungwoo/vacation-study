package com.enjoytrip.member.service;

import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.domain.model.type.Role;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    void loadUserByUsername() {
        // given
        Member member = Member.builder()
                .username("testUser")
                .password("testPassword")
                .name("testName")
                .nickname("testNickname")
                .role(Role.USER)
                .isDeleted(false)
                .build();

        Mockito.when(memberRepository.findByUsernameAndIsDeletedFalse(member.getUsername()))
                .thenReturn(Optional.of(member));

        // when
        Member found = (Member) memberService.loadUserByUsername(member.getUsername());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        // given
        String nonExistingUsername = "doesNotExist";

        Mockito.when(memberRepository.findByUsernameAndIsDeletedFalse(nonExistingUsername))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberService.loadUserByUsername(nonExistingUsername))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void findMemberById() {
        // given
        Member member = Member.builder()
                .id(1L)
                .username("testUser")
                .password("testPassword")
                .name("testName")
                .nickname("testNickname")
                .role(Role.USER)
                .isDeleted(false)
                .build();

        Mockito.when(memberRepository.findByIdAndIsDeletedFalse(1L))
                .thenReturn(Optional.of(member));

        // when
        Member found = memberService.findMemberById(1L);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(member.getId());
    }

    @Test
    void getNicknameByMember() {
        // given
        Member member = Member.builder()
                .username("testUser")
                .password("testPassword")
                .name("testName")
                .nickname("testNickname")
                .role(Role.USER)
                .isDeleted(false)
                .build();

        Mockito.when(memberRepository.findById(member.getId()))
                .thenReturn(Optional.of(member));

        // when
        String foundNickname = memberService.getNicknameByMember(member);

        // then
        assertThat(foundNickname).isNotNull();
        assertThat(foundNickname).isEqualTo(member.getNickname());
    }

    @Test
    void joinMember() {
        // given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .username("testUser")
                .password("testPassword")
                .name("testName")
                .nickname("testNickname")
                .build();

        Mockito.when(memberRepository.save(Mockito.any(Member.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        // when
        memberService.joinMember(memberCreateDto);

        // then
        Mockito.verify(memberRepository, Mockito.times(1)).save(Mockito.any(Member.class));
    }

    @Test
    void updateNickName() {
        // given
        Member member = Mockito.spy(Member.builder()
                .id(1L)
                .username("testUser")
                .password("testPassword")
                .nickname("testNickname")
                .role(Role.USER)
                .isDeleted(false)
                .build());

        String newNickname = "newNickname";

        Mockito.when(memberRepository.findByIdAndIsDeletedFalse(member.getId()))
                .thenReturn(Optional.of(member));

        // when
        memberService.updateNickName(member.getId(), newNickname);

        // then
        Mockito.verify(member, Mockito.times(1)).changeNickname(newNickname);
        assertThat(member.getNickname()).isEqualTo(newNickname);
    }

    @Test
    void updatePassword() {
        // given
        Member member = Mockito.spy(Member.builder()
                .id(1L)
                .username("testUser")
                .password("testPassword")
                .nickname("testNickname")
                .role(Role.USER)
                .isDeleted(false)
                .build());

        String newPassword = "newPassword";

        Mockito.when(memberRepository.findByIdAndIsDeletedFalse(member.getId()))
                .thenReturn(Optional.of(member));

        // when
        memberService.updatePassword(member.getId(), newPassword);

        // then
        Mockito.verify(member, Mockito.times(1)).changePassword(newPassword);
        assertThat(member.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void deleteMember() {
        // given
        Member member = Mockito.spy(Member.builder()
                .id(1L)
                .username("testUser")
                .password("testPassword")
                .nickname("testNickname")
                .role(Role.USER)
                .isDeleted(false)
                .build());

        Mockito.when(memberRepository.findByIdAndIsDeletedFalse(member.getId()))
                .thenReturn(Optional.of(member));

        // when
        memberService.deleteMember(member.getId());

        // then
        Mockito.verify(member, Mockito.times(1)).markAsDeleted();
    }
}
