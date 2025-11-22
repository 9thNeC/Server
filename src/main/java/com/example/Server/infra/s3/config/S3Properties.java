package com.example.Server.infra.s3.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

// AmazonS3 객체를 생성하고, AWS 인증 정보 및 엔드포인트 설정을 읽어와 구성한 뒤, Spring Bean으로 등록하는 설정 클래스
@ConfigurationProperties(prefix = "cloud.aws")
@Getter
@Setter
public class S3Properties {
    private String accessKey;
    private String secretKey;
    private String region;
    private String bucket;
    private String endpoint;
}
