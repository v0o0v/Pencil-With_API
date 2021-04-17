package com.pencilwith.apiserver.start.config.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private Header header;

    private T body;

    public ApiResponse(T body) {
        this(Header.success(), body);
    }

    public ApiResponse(Header header, T body) {
        this.header = header;
        this.body = body;
    }
}
