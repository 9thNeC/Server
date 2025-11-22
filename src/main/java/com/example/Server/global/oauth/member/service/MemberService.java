package com.example.Server.global.oauth.member.service;

import com.example.Server.global.oauth.member.domain.Member;

public interface MemberService {

    Member getMemberBySocialId(String socialId);

    Member createOauth(String socialId, String email);
}

