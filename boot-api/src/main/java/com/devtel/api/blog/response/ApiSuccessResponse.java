package com.devtel.api.blog.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiSuccessResponse<T> {

    // API 응답 성공 결과 Response
    private T result;
    // API 응답 성공 코드 Response
    private int status;
    // API 응답 성공 코드 Response
    private String code;
    // API 응답 성공 코드 Message
    private String message;

    public ApiSuccessResponse(T result, int status, String code, String message) {
        this.result = result;
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static<T> ApiSuccessResponse <T> of(T result) {
        return new ApiSuccessResponse<>(result,200,"0000","SUCCESS");
    }

}
