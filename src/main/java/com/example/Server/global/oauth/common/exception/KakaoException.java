package com.example.Server.global.oauth.common.exception;

import com.example.Server.global.common.error.exception.CustomException;
import com.example.Server.global.common.error.exception.ErrorCode;

public class KakaoException extends CustomException {
    public KakaoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
