package com.example.Server.oauth.member.service;

import com.example.Server.oauth.member.domain.Member;

public interface MemberService {

    Member getMemberBySocialId(String socialId);

    Member createOauth(String socialId, String email);
}

