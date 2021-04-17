package com.pencilwith.apiserver.start;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatus {

    // 2xx Success
    SUCCESS(HttpStatus.OK.value(), "success"),

    // 4xx Client Error
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST.value(), "Invalid request parameter"),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "Forbidden"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "Login required"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Resource not found"),

    // 5xx Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");

    private int code;

    private String reason;

    ResponseStatus(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
