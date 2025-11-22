package com.example.Server.domain.issue.service;

import com.example.Server.domain.challenge.dto.response.CreateChallengeResDto;
import com.example.Server.domain.challenge.entity.Challenge;
import com.example.Server.domain.challenge.repository.ChallengeRepository;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.issue.dto.res.AiResponseResDto;
import com.example.Server.domain.issue.entity.Issue;
import com.example.Server.domain.issue.repository.IssueRepository;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.global.common.error.exception.CustomException;
import com.example.Server.global.common.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssuesService{

    private final IssueRepository issueRepository;
    private final ChallengeRepository challengeRepository;
    private final AiResponseService aiResponseService;

    @Override
    @Transactional
    public CreateChallengeResDto createIssue(CreateIssueReqDto dto, Member member) {

        if(member == null)
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        if(member.getNickname().isEmpty())
            throw new CustomException(ErrorCode.INVALID_NICKNAME_FORMAT);

        Issue issue = Issue.builder()
                .member(member)
                .content(dto.getContent())
                .category(dto.getCategory())
                .build();

        issueRepository.save(issue);

        AiResponseResDto aiResDto = aiResponseService.requestOpenAI(dto);

        String title = aiResDto.getTitle();
        String content = aiResDto.getContent();
        String comfort = aiResDto.getComfort();

        Challenge challenge = Challenge.builder()
                .issue(issue)
                .comfortContent(content)
                .title(title)
                .content(comfort)
                .build();

        challengeRepository.save(challenge);


        return CreateChallengeResDto.builder()
                .title(title)
                .content(content)
                .comfortContent(comfort)
                .build();
    }
}
