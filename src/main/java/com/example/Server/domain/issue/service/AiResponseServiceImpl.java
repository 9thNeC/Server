package com.example.Server.domain.issue.service;

import com.example.Server.domain.issue.dto.res.OpenAiResponse;
import com.example.Server.global.common.error.exception.CustomException;
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
                "너는 20대 청춘이 가지고 잇는 고민을 이야기 하면 이것을 재미있고 해학스럽게 위로 해주고 챌린지를 제안해주는 서비스야.\n" +
                        "아래 조건들과 JSON 형식으로 답해줘 :\n" +

                        "아래 7가지 조건은 위로의 말 조건이야.\n" +
                        "1. 위로는 재미있고, 위트있고 해학스러워야해.\n" +
                        "2. 과장되게 하면서 유저를 웃기게 해야해.\n" +
                        "3. 비속어와 욕설은 사용하면 안돼.\n" +
                        "4. 한글로만 해야해.\n" +
                        "5. 글자수는 120자 이하여야해\n" +
                        "6. 볼드체 하지마.\n" +
                        "7. 이모티콘 안돼.\n" +

                        "아래 2가지 조건은 챌린지 제목의 조건이야.\n" +
                        "1. 챌린지 제목은 10글자 이내여야해..\n" +
                        "2. 챌린지 내용을 쉽게 알수 있도록 직관적이여야해.\n" +

                        "아래 7가지 조건은 챌린지 내용의 조건이야.\n" +
                        "1. 챌린지는 재미있고, 위트있고 해학스러워야해.\n" +
                        "2. 과장되게 하면서 유저를 웃기게 해야해.\n" +
                        "3. 비속어와 욕설은 사용하면 안돼.\n" +
                        "4. 한글로만 해야해.\n" +
                        "5. 글자수는 120자 이하여야해.\n" +
                        "6. 볼드체 하지마.\n" +
                        "7. 이모티콘 안돼.\n" +

                        "아래 조건은 너가 답해야할 JSON 형식이야..\n" +
                        "{\n" +
                        "  \"comfort\": \"위로의 말\",\n" +
                        "  \"title\": \"챌린지 제목\",\n" +
                        "  \"content\": \"재미있고 해학적인 챌린지 한가지\"\n" +
                        "}\n" +
                        "고민 내용: \"%s\"", concernContent
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4");
        body.put("messages", List.of(Map.of(
                "role", "user",
                "content", prompt
        )));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<OpenAiResponse> response =
                restTemplate.postForEntity(url, request, OpenAiResponse.class);


        OpenAiResponse openAiResponse = response.getBody();

        if(openAiResponse == null || openAiResponse.getChoices() == null || openAiResponse.getChoices().isEmpty()) {
            throw new CustomException(ErrorCode.AI_RESPONSE_FAILED);
        }

        String content = openAiResponse.getChoices().get(0).getMessage().getContent();

        try {
            AiResponseResDto aiResponse = objectMapper.readValue(content, AiResponseResDto.class);
            if(aiResponse == null) {
                throw new CustomException(ErrorCode.AI_RESPONSE_FAILED);
            }
            return aiResponse;
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.AI_RESPONSE_FAILED);
        }

    }
}
