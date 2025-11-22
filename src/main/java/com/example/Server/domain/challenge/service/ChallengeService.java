package com.example.Server.domain.challenge.service;

import com.example.Server.domain.challenge.dto.request.ChallengeVerificationReqDto;
import com.example.Server.domain.challenge.dto.response.*;
import com.example.Server.domain.challenge.entity.Challenge;
import com.example.Server.domain.challenge.repository.ChallengeRepository;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.global.common.error.exception.CustomException;
import com.example.Server.global.common.error.exception.ErrorCode;
import com.example.Server.global.type.Category;
import com.example.Server.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    public ChallengeListResDto challengeList(String category) {
        Member member = MemberUtil.getCurrentMember();

        List<Challenge> challenges = null;
        if(category == null) {
            challenges = challengeRepository.findChallengesByMemberOrderByCreatedAtDesc(member.getId());
        }

        challenges = getChallenges(member, toCategory(category));
        List<ChallengeListItemDto> dtoList =
                challenges.stream()
                        .map(ChallengeListItemDto::from)
                        .toList();
        return new ChallengeListResDto(member.getNickname(), dtoList);
    }

    public List<Challenge> getChallenges(Member member, Category category) {
        return challengeRepository.findChallengesByMemberAndIssueCategory(member.getId(), category);
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

    public DetailChallengeResDto detailChallenge(Long challengeId) {
        Challenge challenge = getChallenge(challengeId);
        verifyMember(MemberUtil.getCurrentMember(), challenge);
        return new DetailChallengeResDto(challenge.getId(), challenge.getIssue().getCategory().toString(), challenge.getImageUrl(), challenge.getComfortContent());
    }

    private void verifyMember(Member currentMember, Challenge challenge) {
        if(!Objects.equals(currentMember, challenge.getMember())) {
            throw new CustomException(ErrorCode.NOT_AUTHORIZED_CHALLENGE);
        }
    }

    public DetailChallengeWithIssueResDto detailChallengeWithIssue(Long challengeId) {
        Member member = MemberUtil.getCurrentMember();

        Challenge challenge = getChallenge(challengeId);
        verifyMember(member, challenge);

        return new DetailChallengeWithIssueResDto(challenge.getId(), member.getNickname(), challenge.getCreatedAt(), challenge.getIssue().getContent(), challenge.getComfortContent(), challenge.getTitle(), challenge.getContent());
    }

    private Challenge getChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_CHALLENGE));
    }

    public ChallengeVerificationResDto challengeVerification(Long challengeId, ChallengeVerificationReqDto reqDto) {

        Challenge challenge = getChallenge(challengeId);
        verifyMember(MemberUtil.getCurrentMember(), challenge);
        challenge.update(reqDto.getImageUrl());

        return new ChallengeVerificationResDto(challenge.getId(), challenge.getImageUrl(), "챌린지가 성공적으로 인증되었습니다.");
    }
}
