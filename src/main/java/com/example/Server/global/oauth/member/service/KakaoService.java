package com.example.Server.global.oauth.member.service;

import com.example.Server.global.common.error.exception.ErrorCode;
import com.example.Server.global.oauth.common.exception.KakaoException;
import com.example.Server.global.oauth.member.dto.AccessTokenDto;
import com.example.Server.global.oauth.member.dto.KakaoProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoService {

    @Value("${oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    public AccessTokenDto getAccessToken(String code) {
        try {
            RestClient restClient = RestClient.create(); // RestClient 객체 생성

            // MultiValueMap을 통해 자동으로 form-data형식으로 body 조립 가능
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", kakaoClientId);
            params.add("client_secret", kakaoClientSecret);
            params.add("redirect_uri", kakaoRedirectUri);
            params.add("grant_type", "authorization_code");

            ResponseEntity<AccessTokenDto> response = restClient.post()
                    .uri("https://kauth.kakao.com/oauth/token") // token_uri
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(params)
                    .retrieve()
                    .toEntity(AccessTokenDto.class);
            System.out.println("응답 accesstoken JSON " + response.getBody());
            if (response.getBody() == null) {
                throw new KakaoException(ErrorCode.KAKAO_TOKEN_REQUEST_FAILED);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new KakaoException(ErrorCode.KAKAO_TOKEN_REQUEST_FAILED);
        }


    }

    public KakaoProfileDto getKakaoProfile(String token) {
        RestClient restClient = RestClient.create();

        ResponseEntity<KakaoProfileDto> response = restClient.get()
                .uri("https://kapi.kakao.com/v2/user/me") // token_uri
                .header("Authorization","Bearer " + token)
                .retrieve()
                .toEntity(KakaoProfileDto.class);
        System.out.println("profile JSON: " + response.getBody());
        return response.getBody();
    }

}

