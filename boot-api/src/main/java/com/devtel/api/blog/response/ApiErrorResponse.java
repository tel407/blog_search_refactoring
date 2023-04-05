package com.devtel.api.blog.response;

import com.devtel.api.blog.config.exception.ErrorCodeEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

    private int status;
    private String code;
    private String message;
    private String errors;

    public ApiErrorResponse(ErrorCodeEnum code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }
    public ApiErrorResponse(ErrorCodeEnum code, String errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = errors;
    }

    public static ApiErrorResponse of(ErrorCodeEnum code) {
        return new ApiErrorResponse(code);
    }
    public static ApiErrorResponse of(ErrorCodeEnum code, BindingResult bindingResult) {
        final String error = bindingResult.getFieldError() == null ? "" : "["+ bindingResult.getFieldError().getField() +"]"+ bindingResult.getFieldError().getDefaultMessage();
        return new ApiErrorResponse(code, error);
    }
    public static ApiErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String error = e.getValue() == null ? "" : "[" +e.getName()+"]" + e.getValue().toString();
        return new ApiErrorResponse(ErrorCodeEnum.INVALID_TYPE_VALUE, error);
    }


}
