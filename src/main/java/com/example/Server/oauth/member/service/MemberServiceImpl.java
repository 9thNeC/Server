package com.example.Server.oauth.member.service;

import com.example.Server.oauth.member.domain.Member;
import com.example.Server.oauth.member.domain.Role;
import com.example.Server.oauth.member.repository.MemberRepository;
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
    public Member createOauth(String socialId, String email) {
        Member newMember = Member.builder()
                .socialId(socialId)
                .email(email)
                .role(Role.USER)
                .build();

        return memberRepository.save(newMember);
    }
}

