package com.example.Server.domain.test.controller;

import com.example.Server.domain.test.dto.TestResponseDto;
import com.example.Server.global.common.error.exception.CustomException;
import com.example.Server.global.common.error.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(
            summary = "건강 체크 API",
            description = "서버가 정상 동작하는지 확인하는 테스트 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "테스트 성공 메시지",
                    content = @Content(schema = @Schema(implementation = TestResponseDto.class))
            )
    })
    @GetMapping("/health")
    public TestResponseDto healthCheck() {
        return new TestResponseDto("테스트에 성공하였습니다!");
    }

    @Operation(
            summary = "에러 테스트 API",
            description = "CustomException 발생 여부를 테스트하는 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "TEST_ERROR_CODE에 해당하는 커스텀 에러",
                    content = @Content(schema = @Schema(implementation = TestResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 에러",
                    content = @Content(schema = @Schema())
            )
    })
    @GetMapping("/error")
    public void errorCheck() {
        throw new CustomException(ErrorCode.TEST_ERROR_CODE);
    }
}
