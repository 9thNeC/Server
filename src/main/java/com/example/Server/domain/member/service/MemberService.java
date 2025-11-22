package com.example.Server.domain.member.service;

import com.example.Server.domain.member.dto.NicknameRequestDto;
import com.example.Server.domain.member.dto.UpdateNicknameResponseDto;
import com.example.Server.domain.member.entity.Member;

public interface MemberService {

    Member getMemberBySocialId(String socialId);

    Member createOauth(String socialId);
    void updateNickname(String socialId, String nickname);

}

