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
    KAKAO_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}