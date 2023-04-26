package com.kaka.exception;

import com.kaka.common.BaseResponse;
import com.kaka.common.ErrorCode;
import com.kaka.common.ResultUtils;
import com.kaka.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author kaka
 * 全局异常处理器
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException:" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e, String message, String description) {
        log.error("runtimeException:" + e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, message, description);
    }
}
