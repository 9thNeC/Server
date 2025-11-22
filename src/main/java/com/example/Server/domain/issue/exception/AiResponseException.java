package com.example.Server.domain.issue.exception;

import com.example.Server.global.common.error.exception.CustomException;
import com.example.Server.global.common.error.exception.ErrorCode;

public class AiResponseException extends CustomException {

    public AiResponseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AiResponseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode);
        initCause(cause);
    }
}

