package com.example.Server.domain.challenge.service;

import com.example.Server.domain.challenge.dto.response.ChallengeListItemDto;
import com.example.Server.domain.challenge.dto.response.ChallengeListResDto;
import com.example.Server.domain.challenge.entity.Challenge;
import com.example.Server.domain.challenge.repository.ChallengeRepository;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.global.common.error.exception.CustomException;
import com.example.Server.global.common.error.exception.ErrorCode;
import com.example.Server.global.type.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    public ChallengeListResDto challengeList(String category) {
        // 현재 사용자 조회 (현재 사용자의 챌린지만 조회)
        Member member = null;
        List<ChallengeListItemDto> dtoList =
                getChallenges(member, toCategory(category)).stream()
                        .map(ChallengeListItemDto::from)   // 여기!
                        .toList();
        return new ChallengeListResDto("닉네임", dtoList);
    }

    public List<Challenge> getChallenges(Member member, Category category) {

        if (category == null) {
            return challengeRepository.findByMemberOrderByCreatedAtDesc(member);
        }

        return challengeRepository.findByMemberAndIssue_CategoryOrderByCreatedAtDesc(member, category);
    }

    public static Category toCategory(String category) throws CustomException {
        if (category == null || category.isBlank()) {
            return null;  // 전체 조회용
        }

        try {
            return Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY);
        }
    }

}
