package com.pencilwith.apiserver.start.config.response;

import lombok.Getter;

@Getter
public class Header {

    private static Header SUCCESS;

    private int code;

    private String reason;

    private String detail;

    public static final Header success() {
        if (SUCCESS == null) {
            SUCCESS = new Header(ResponseStatus.SUCCESS);
        }
        return SUCCESS;
    }

    public Header(ResponseStatus status) {
        this(status, null);
    }

    public Header(ResponseStatus status, String detail) {
        this.code = status.getCode();
        this.reason = status.getReason();
        this.detail = detail;
    }
}
