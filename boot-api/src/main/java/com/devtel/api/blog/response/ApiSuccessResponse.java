package com.devtel.api.blog.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiSuccessResponse<T> {

    private static final String SUCCESS_STATUS = "Success";
    private String status;
    private T data;

    public static <T> ApiSuccessResponse<T> createSuccess(T data) {
        return new ApiSuccessResponse<>(SUCCESS_STATUS, data);
    }

    private ApiSuccessResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }


}
