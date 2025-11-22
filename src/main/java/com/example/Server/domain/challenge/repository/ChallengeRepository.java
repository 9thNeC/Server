package com.example.Server.domain.challenge.repository;

import com.example.Server.domain.challenge.entity.Challenge;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.global.type.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // category 없을 때 (전체 조회)
    List<Challenge> findByMemberOrderByCreatedAtDesc(Member member);

    // category 있을 때 (Issue.category 기준으로 조회)
    List<Challenge> findByMemberAndIssue_CategoryOrderByCreatedAtDesc(
            Member member,
            Category category
    );
}
