package com.devtel.api.blog.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * [공통 코드] API 통신에 대한 '에러 코드'를 Enum 형태로 관리를 한다.
 * Global Error CodeList : 전역으로 발생하는 에러코드를 관리한다.
 * Custom Error CodeList : 업무 페이지에서 발생하는 에러코드를 관리한다
 * Error Code Constructor : 에러코드를 직접적으로 사용하기 위한 생성자를 구성한다.
 */
@Getter
public enum ErrorCodeEnum {

        /**
         * ******************************* Common Error CodeList ***************************************
         * HTTP Status Code
         * 400 : Bad Request
         * 401 : Unauthorized
         * 403 : Forbidden
         * 404 : Not Found
         * 500 : Internal Server Error
         * *********************************************************************************************
         */

        // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
        NOT_VALID_ERROR(404, "C000011", "handle Validation Exception"),

        // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
        NOT_VALID_HEADER_ERROR(404, "C000012", "Header에 데이터가 존재하지 않는 경우 "),
        // 허용되지 않은 메서드입니다
        METHOD_NOT_ALLOWED(405, "C000013","허용되지 않은 메서드입니다."),
        // 인증 권한이 없습니다
        HANDLE_ACCESS_DENIED(403, "C000014", "Access is Denied"),

        // 잘못된 서버 요청
        BAD_REQUEST_ERROR(400, "C000001", "Bad Request Exception"),

        // @RequestBody 데이터 미 존재
        REQUEST_BODY_MISSING_ERROR(400, "C000002", "Required request body is missing"),

        // 유효하지 않은 타입
        INVALID_TYPE_VALUE(400, "C000003", " Invalid Type Value"),

        // Request Parameter 로 데이터가 전달되지 않을 경우
        MISSING_REQUEST_PARAMETER_ERROR(400, "C000004", "Missing Servlet RequestParameter Exception"),

        // 입력/출력 값이 유효하지 않음
        IO_ERROR(400, "C000005", "I/O Exception"),

        // com.google.gson JSON 파싱 실패
        JSON_PARSE_ERROR(400, "C000006", "JsonParseException"),

        // com.fasterxml.jackson.core Processing Error
        JACKSON_PROCESS_ERROR(400, "C000007", "com.fasterxml.jackson.core Exception"),

        // 권한이 없음
        FORBIDDEN_ERROR(403, "C000008", "Forbidden Exception"),

        // 서버로 요청한 리소스가 존재하지 않음
        NOT_FOUND_ERROR(404, "C000009", "Not Found Exception"),

        // NULL Point Exception 발생
        NULL_POINT_ERROR(404, "C000010", "Null Point Exception"),

        // 서버가 처리 할 방법을 모르는 경우 발생
        INTERNAL_SERVER_ERROR(500, "C000999", "Internal Server Error Exception"),


        /**
         * ******************************* Business Error CodeList ***************************************
         */
        // Transaction Insert Error
        INSERT_ERROR(200, "B000001", "Insert Transaction Error Exception"),

        // Transaction Update Error
        UPDATE_ERROR(200, "B000002", "Update Transaction Error Exception"),

        // Transaction Delete Error
        DELETE_ERROR(200, "B000003", "Delete Transaction Error Exception"),

        BLOG_VENDOR_ALL_ERROR(200, "B000004", "모든 검색엔진 사용 불가."),
        BLOG_VENDOR_KAKAO_ERROR(200, "B000005", "카카오 검색엔진 사용 불가."),
        BLOG_VENDOR_NAVER_ERROR(200, "B000006", "네이버 검색엔진 사용 불가."),

        ; // End

        // 에러의 '코드 상태'을 반환한다.
        private final int status;

        // 에러의 '서비스의 코드간 구분 값'을 반환한다.
        private final String code;

        // 에러의 '코드 메시지'을 반환한다.
        private final String message;

        // 생성자 구성
        ErrorCodeEnum(final int status, final String code, final String message) {
                this.status = status;
                this.code = code;
                this.message = message;
        }

}
