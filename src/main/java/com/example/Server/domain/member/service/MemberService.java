package com.example.Server.domain.member.service;

import com.example.Server.domain.member.dto.NicknameRequestDto;
import com.example.Server.domain.member.dto.UpdateNicknameResponseDto;
import com.example.Server.domain.member.entity.Member;

public interface MemberService {

    Member getMemberBySocialId(String socialId);

    Member createOauth(String socialId, String email);
    UpdateNicknameResponseDto updateNickname(String socialId, String nickname);

}

