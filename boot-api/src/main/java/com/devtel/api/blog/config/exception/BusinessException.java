package com.devtel.api.blog.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
	private final ErrorCodeEnum errorCode;

	public BusinessException(String message, ErrorCodeEnum errorCode){
		super(message);
		this.errorCode = errorCode;
	}
}
