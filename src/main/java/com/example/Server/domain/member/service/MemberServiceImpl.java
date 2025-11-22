package com.example.Server.domain.member.service;

import com.example.Server.domain.member.dto.NicknameRequestDto;
import com.example.Server.domain.member.dto.UpdateNicknameResponseDto;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.domain.member.entity.Role;
import com.example.Server.domain.member.repository.MemberRepository;
import com.example.Server.global.common.error.exception.CustomException;
import com.example.Server.global.common.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member getMemberBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId).orElse(null);
    }

    @Override
    public Member createOauth(String socialId) {
        Member newMember = Member.builder()
                .socialId(socialId)
                .role(Role.USER)
                .build();

        return memberRepository.save(newMember);
    }

    @Override
    public UpdateNicknameResponseDto updateNickname(String socialId, String newNickname) {
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        member.updateNickname(newNickname);  // 엔티티 메서드 호출
        memberRepository.save(member);

        return new UpdateNicknameResponseDto(newNickname, "정상적으로 닉네임이 설정되었습니다.");
    }

}

