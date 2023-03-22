package com.devtel.api.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

        EMPTY_VALID_KEYWORD(BAD_REQUEST, "블로그 검색어는 필수 값입니다."),
        BLOG_API_SERVICE_ERROR(INTERNAL_SERVER_ERROR, "모든 API 통신장애, 잠시후 다시 시도해주세요"),
        NAVER_BLOG_API_SERVICE_ERROR(INTERNAL_SERVER_ERROR, "현재 네이버 API 통신이 불가능합니다."),
        KAKAO_BLOG_API_SERVICE_ERROR(INTERNAL_SERVER_ERROR, "현재 카카오 API 통신이 불가능합니다.")
        ;

        private final HttpStatus code;
        private final String detail;

        /* 키워드 Validtaion*/
        public static CustomException throwEmptyKeywordValid() {
                throw new CustomException(EMPTY_VALID_KEYWORD);
        }
        /* 전체 통신장애 */
        public static CustomException throwNotConnectionApi() {
                throw new CustomException(BLOG_API_SERVICE_ERROR);
        }
        /* 카카오 통신장애 */
        public static CustomException throwKakaoApiServiceError() {
                throw new CustomException(KAKAO_BLOG_API_SERVICE_ERROR);
        }
        /* 네이버 통신장애 */
        public static CustomException throwNaverApiServiceError() {
                throw new CustomException(NAVER_BLOG_API_SERVICE_ERROR);
        }


}
