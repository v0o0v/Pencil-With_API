package com.pencilwith.apiserver.domain.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message) {
        super(message);
    }

}