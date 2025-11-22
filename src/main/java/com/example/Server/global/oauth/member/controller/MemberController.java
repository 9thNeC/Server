package com.example.Server.global.oauth.member.controller;

import com.example.Server.global.common.error.ErrorResponse;
import com.example.Server.global.jwt.JwtTokenProvider;
import com.example.Server.global.oauth.member.domain.Member;
import com.example.Server.global.oauth.member.dto.AccessTokenDto;
import com.example.Server.global.oauth.member.dto.KakaoProfileDto;
import com.example.Server.global.oauth.member.dto.RedirectDto;
import com.example.Server.global.oauth.member.service.KakaoService;
import com.example.Server.global.oauth.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoService kakaoService;


    @Operation(
            summary = "카카오 로그인 API",
            description = "카카오 인가 코드를 전달받아 로그인 또는 자동 회원가입 후 JWT를 발급합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "카카오 로그인 성공",
                    content = @Content(
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "KAKAO_INVALID_CODE / KAKAO_TOKEN_REQUEST_FAILED / KAKAO_PROFILE_REQUEST_FAILED 등 카카오 인증 관련 커스텀 에러",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "카카오 서버 오류 또는 예기치 못한 서버 내부 에러",
                    content = @Content(schema = @Schema())
            )
    })
    @PostMapping("/kakao/doLogin")
    public ResponseEntity<?> kakaoLogin(@RequestBody RedirectDto redirectDto) {
        // accesstoken 발급
        AccessTokenDto accessTokenDto = kakaoService.getAccessToken(redirectDto.getCode());

        // 사용자정보 얻기
        KakaoProfileDto kakaoProfileDto = kakaoService.getKakaoProfile(accessTokenDto.getAccess_token());

        // 회원가입이 되어 있지 않다면 회원가입
        Member originalMember = memberService.getMemberBySocialId(kakaoProfileDto.getId()); // kakaoProfileDto의 정보 중 sub를 통해서 db에 회원이 있는지 확인
        if(originalMember == null) { // Oauth를 통해서 회원가입할 겨우 openid, email, 소셜타입을 넘겨준다.
            originalMember = memberService.createOauth(kakaoProfileDto.getId(), kakaoProfileDto.getKakao_account().getEmail()); // kakao oauth 회원가입
        }

        // 회원가입돼 있는 회원이라면 토큰 발급
        String jwtToken = jwtTokenProvider.createToken(originalMember.getEmail(), originalMember.getRole().toString());

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("id", originalMember.getId());
        loginInfo.put("token", jwtToken);
        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }
}
