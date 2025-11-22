package com.example.Server.domain.issue.service;

import com.example.Server.domain.issue.dto.res.OpenAiResponse;
import com.example.Server.domain.issue.exception.AiResponseException;
import com.example.Server.global.common.error.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.issue.dto.res.AiResponseResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiResponseServiceImpl implements AiResponseService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private final String url = "https://api.openai.com/v1/chat/completions";

    @Override
    public AiResponseResDto requestOpenAI(CreateIssueReqDto dto) {

        String concernContent = dto.getContent();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        // JSON 형식 응답 요청
        String prompt = String.format(
                "당신은 고민 상담 AI입니다. 아래 JSON 형식으로 답해주세요:\n" +
                        "{\n" +
                        "  \"comfort\": \"위로의 말\",\n" +
                        "  \"title\": \"챌린지 제목\",\n" +
                        "  \"content\": \"재미있고 해학적인 챌린지 한가지\"\n" +
                        "}\n" +
                        "고민 내용: \"%s\"", concernContent
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", List.of(Map.of(
                "role", "user",
                "content", prompt
        )));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<OpenAiResponse> response =
                restTemplate.postForEntity(url, request, OpenAiResponse.class);


        OpenAiResponse openAiResponse = response.getBody();

        if(openAiResponse == null || openAiResponse.getChoices() == null || openAiResponse.getChoices().isEmpty()) {
            throw new AiResponseException(ErrorCode.AI_RESPONSE_FAILED);
        }

        String content = openAiResponse.getChoices().get(0).getMessage().getContent();

        try {
            AiResponseResDto aiResponse = objectMapper.readValue(content, AiResponseResDto.class);
            if(aiResponse == null) {
                throw new AiResponseException(ErrorCode.AI_RESPONSE_FAILED);
            }
            return aiResponse;
        } catch (JsonProcessingException e) {
            throw new AiResponseException(ErrorCode.AI_RESPONSE_FAILED, e);
        }

    }
}
