package com.enjoytrip.member.repository;

import com.enjoytrip.domain.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.username = :username and m.isDeleted = false")
    Optional<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.nickname = :nickname and m.isDeleted = false")
    Optional<Member> findMemberByNickname(@Param("nickname") String nickname);

    @Query("select m from Member m where m.id = :id and m.isDeleted = false ")
    Optional<Member> findById(Long id);

//    @Modifying
//    @Query("update Member m set m.isDeleted = true where m.id = :id and m.isDeleted = false")
//    void deleteMember(@Param("id") Long id);
//
//    @Modifying
//    @Query("update Member m set m.nickname = :nickname where m.id = :id")
//    void updateNickname(@Param("id") Long id, @Param("nickname") String newNickname);
//
//    @Modifying
//    @Query("update Member m set m.password = :newPassword where m.id = :id")
//    void updatePassword(@Param("id") Long id, @Param("newPassword") String newPassword);
}
