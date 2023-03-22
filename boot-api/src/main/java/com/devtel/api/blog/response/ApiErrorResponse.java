package com.devtel.api.blog.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse<T> {

    private static final String ERROR_STATUS = "Fail";

    private String status;
    private HttpStatus code;
    private String message;
    private String errors;

    public static ApiErrorResponse<?> createError(HttpStatus code, String message, String errors) {
        return new ApiErrorResponse<>(ERROR_STATUS, code,  message, errors);
    }

    public ApiErrorResponse(String status, HttpStatus code, String message, String errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

}
