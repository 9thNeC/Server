package com.example.Server.domain.member.controller;

import com.example.Server.domain.member.CustomUserDetails;
import com.example.Server.domain.member.dto.*;
import com.example.Server.global.common.error.ErrorResponse;
import com.example.Server.global.jwt.JwtTokenProvider;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.domain.member.service.KakaoService;
import com.example.Server.domain.member.service.MemberService;
import com.example.Server.global.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
            originalMember = memberService.createOauth(kakaoProfileDto.getId(), kakaoProfileDto.getKakao_account().getProfile().getNickname()); // kakao oauth 회원가입
        }

        // 회원가입돼 있는 회원이라면 토큰 발급(SocialId 기반)
        String jwtToken = jwtTokenProvider.createToken(originalMember.getSocialId(), originalMember.getRole().toString());

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("id", originalMember.getId());
        loginInfo.put("token", jwtToken);
        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails
) {
            return ResponseEntity.ok(MemberUtil.getCurrentMember());
        }

    @PatchMapping("/nickname")
    public ResponseEntity<UpdateNicknameResponseDto> updateNickname(
            @RequestBody NicknameRequestDto request) {

        Member member = MemberUtil.getCurrentMember();

        if (member == null) {
            return ResponseEntity.status(401).build();
        }

        UpdateNicknameResponseDto response = memberService.updateNickname(
                member.getSocialId(),
                request.getNickname()
        );

        return ResponseEntity.ok(response);
    }





}
