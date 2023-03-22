package com.devtel.api.blog.vender;

import com.devtel.api.blog.dto.BlogDto;
import com.devtel.api.blog.vender.naver.NaverBlogApiDto;

import java.util.List;

public interface IVenderBlog<T,K> {
    /**
     *  벤더사 블로그 정보 명세화된 정보 가져오기
     */
    BlogDto.BlogResultDto getBlogPostingResult(BlogDto.BlogSearchDto blogSearchDto);
    /**
     *  벤더사 블로그 요청 PARAMETER -> API 요청 PRARAMETER로 가공
     */
    T convertParamToApiDto(BlogDto.BlogSearchDto blogSearchDto);
    /**
     *  벤더사 블로그 API 호출
     */
    K callRequestBlogApi(T apiRequestDto);
    /**
     *  벤터사 API 응답 값 -> 블로그 검색 서비스 DTO 로 가공
     */
    BlogDto.BlogResultDto convertResDtoToBlogDto(T apiRequestDto, K apiResponDto);
}
