package com.enjoytrip.member.repository;

import com.enjoytrip.domain.model.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsernameAndIsDeletedFalse(String username);

    Optional<Member> findByIdAndIsDeletedFalse(Long id);
}
