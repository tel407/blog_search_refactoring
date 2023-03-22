package com.devtel.api.blog.exception;

import com.devtel.api.blog.response.ApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * 스프링 오류 체크
	 * 지원하지 않는 HTTP 메서드로 요청 405
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(e.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		Set<HttpMethod> supportedHttpMethods = e.getSupportedHttpMethods();
		if (supportedHttpMethods != null) {
			supportedHttpMethods.forEach(t -> builder.append(t).append(" "));
		}
		return ResponseEntity.status(METHOD_NOT_ALLOWED).body(ApiErrorResponse.createError(METHOD_NOT_ALLOWED,e.getLocalizedMessage(),builder.toString()));
	}
	/**
	 * 커스텀 오류 체크 ErrorCodeEnum.class 참조
	 */
	@ExceptionHandler(value = CustomException.class)
	protected ResponseEntity<ApiErrorResponse<?>> handleCustomException(CustomException e) {
		ErrorCodeEnum errorCode = e.getErrorCode();
		return ResponseEntity.status(errorCode.getCode()).body(ApiErrorResponse.createError(errorCode.getCode(),errorCode.getDetail(),""));
	}
	/**
	 * 그외 Exception
	 */
	@ExceptionHandler(value ={Exception.class, RuntimeException.class})
	public ResponseEntity<ApiErrorResponse<?>> handleNoOtherExceptions(Exception e) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiErrorResponse.createError(BAD_REQUEST,e.getLocalizedMessage(), "오류가 발생했습니다."));
	}

}
