package com.example.Server.global.common.error.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEST_ERROR_CODE(HttpStatus.BAD_REQUEST, "오류가 발생하였습니다."),

    // 카카오 로그인 관련 에러
    KAKAO_INVALID_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 카카오 인가 코드입니다."),
    KAKAO_TOKEN_REQUEST_FAILED(HttpStatus.BAD_REQUEST, "카카오 Access Token 요청에 실패했습니다."),
    KAKAO_PROFILE_REQUEST_FAILED(HttpStatus.BAD_REQUEST, "카카오 사용자 정보 조회에 실패했습니다."),
    KAKAO_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 서버 오류가 발생했습니다."),

    // AI 응답 관련 에러
    AI_RESPONSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AI 응답 파싱에 실패했습니다.");
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리 값입니다."),
    NOT_EXIST_CHALLENGE(HttpStatus.NOT_FOUND, "존재하지 않는 challengeId 입니다." );

    private final HttpStatus status;
    private final String message;
}