package com.enjoytrip.member.repository;

import com.enjoytrip.domain.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.username = :username")
    Optional<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.nickname = :nickname")
    Optional<Member> findMemberByNickname(@Param("nickname") String nickname);
}
