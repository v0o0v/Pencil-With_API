package com.pencilwith.apiserver.start;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private Header header;

    private T body;

    public ApiResponse(Header header, T body) {
        this.header = header;
        this.body = body;
    }
}
