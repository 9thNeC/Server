package com.example.Server.domain.challenge.repository;

import com.example.Server.domain.challenge.entity.Challenge;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.global.type.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query(
            value = "SELECT c.* " +
                    "FROM challenges c " +
                    "WHERE c.member_id = :memberId " +
                    "ORDER BY c.created_at DESC",
            nativeQuery = true
    )
    List<Challenge> findChallengesByMemberOrderByCreatedAtDesc(
            @Param("memberId") Long memberId
    );

    // category 있을 때 (Issue.category 기준으로 조회)
    @Query(
            value = "SELECT c.* " +
                    "FROM challenges c " +
                    "JOIN issues i ON c.issue_id = i.id " +
                    "WHERE c.member_id = :memberId " +
                    "AND i.category = :category " +
                    "ORDER BY c.created_at DESC",
            nativeQuery = true
    )
    List<Challenge> findChallengesByMemberAndIssueCategory(
            @Param("memberId") Long memberId,
            @Param("category") Category category
    );

}
