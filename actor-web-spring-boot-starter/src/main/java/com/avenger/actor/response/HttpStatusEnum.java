package com.avenger.actor.response;

public enum HttpStatusEnum {
    SUCCESS(0, "success"),
    BAD_REQUEST(20400, "\u8BF7\u6C42\u9519\u8BEF"),
    UNAUTHORIZED(20401, "\u9274\u6743\u5931\u8D25"),
    NOT_FOUND(20404, "\u8D44\u6E90\u6CA1\u6709\u627E\u5230"),
    METHOD_NOT_ALLOWED(20405, "\u8BF7\u6C42\u65B9\u6CD5\u4E0D\u652F\u6301"),
    NOT_ACCEPTABLE(20406, "\u8BF7\u6C42\u4E0D\u63A5\u53D7"),
    CONFLICT(20409, "\u6821\u9A8C\u6570\u636E\u5931\u8D25"),
    LOCKED(20423, "\u8D44\u6E90\u88AB\u9501\u5B9A"),
    INTERNAL_SERVER_ERROR(20500, "\u670D\u52A1\u5668\u9519\u8BEF"),
    BAD_GATEWAY(20502, "\u7F51\u5173\u9519\u8BEF"),
    SERVICE_UNAVAILABLE(20503, "\u670D\u52A1\u4E0D\u53EF\u7528"),
    ACCOUNT_NOTFOUND(20701, "\u5E10\u53F7\u6216\u5BC6\u7801\u9519\u8BEF"),
    ACCOUNT_INVALID(20702, "\u5E10\u53F7\u5F02\u5E38"),
    ACCOUNT_SUSPEND(20703, "\u5E10\u53F7\u4E0D\u53EF\u7528"),
    PASSWORD_INCORRECT(20704, "\u5E10\u53F7\u6216\u5BC6\u7801\u9519\u8BEF"),
    ACCOUNT_ALREADY_EXIST(20705, "\u5E10\u53F7\u5DF2\u5B58\u5728"),
    CAPTCHA_TYPE_ERROR(20601, "\u9A8C\u8BC1\u7801\u7C7B\u578B\u9519\u8BEF"),
    CAPTCHA_ERROR(20602, "\u9A8C\u8BC1\u7801\u9519\u8BEF"),
    CAPTCHA_INVALID(20603, "\u9A8C\u8BC1\u7801\u5931\u6548"),
    CAPTCHA_OVER_LIMIT(20604, "\u9A8C\u8BC1\u7801\u9A8C\u8BC1\u6B21\u6570\u8D85\u8FC7\u9650\u5236"),
    CLIENTID_EMPTY(20801, "clientId\u4E3A\u7A7A"),
    TOKEN_EMPTY(20802, "token\u4E3A\u7A7A"),
    TIMESTAMP_INVALID(20803, "\u975E\u6CD5\u7684\u65F6\u95F4\u6233"),
    CLIENTID_NOTFOUND(20804, "\u627E\u4E0D\u5230clientId"),
    TOKEN_INCORRECT(20805, "token\u9519\u8BEF");

    private final int status;

    private final String message;

    HttpStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int status() {
        return this.status;
    }

    public String message() {
        return this.message;
    }

    public String toString() {
        return this.status + ": " + this.message;
    }
}
