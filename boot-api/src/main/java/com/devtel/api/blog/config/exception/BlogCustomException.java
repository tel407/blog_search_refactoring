package com.devtel.api.blog.config.exception;

public class BlogCustomException extends BusinessException {

	public BlogCustomException(ErrorCodeEnum errorCode) {
		super(errorCode);
	}

	public BlogCustomException(String message, ErrorCodeEnum errorCode) {
		super(message, errorCode);
	}
}
