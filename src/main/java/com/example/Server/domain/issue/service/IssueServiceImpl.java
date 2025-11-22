package com.example.Server.domain.issue.service;

import com.example.Server.domain.challenge.dto.response.CreateChallengeResDto;
import com.example.Server.domain.challenge.entity.Challenge;
import com.example.Server.domain.challenge.repository.ChallengeRepository;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.issue.dto.res.AiResponseResDto;
import com.example.Server.domain.issue.entity.Issue;
import com.example.Server.domain.issue.repository.IssueRepository;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssuesService{

    private final IssueRepository issueRepository;
    private final ChallengeRepository challengeRepository;
    private final MemberRepository memberRepository;
    private final AiResponseService aiResponseService;

    @Override
    public CreateChallengeResDto createIssue(CreateIssueReqDto dto) {

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Member not found"));


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
