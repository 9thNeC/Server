package com.example.Server.domain.issue.service;

import com.example.Server.domain.challenge.dto.response.CreateChallengeResDto;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.member.entity.Member;

public interface IssuesService {

    CreateChallengeResDto createIssue(CreateIssueReqDto dto);
}
