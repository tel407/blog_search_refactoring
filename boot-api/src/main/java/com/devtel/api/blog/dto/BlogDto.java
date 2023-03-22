package com.devtel.api.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BlogDto {

    /**
     * 블로그 검색 API REQUEST 파라미터
     */
    @Getter
    @Builder
    public static class BlogSearchDto {
        private String keyword;
        private String sort;
        private int page;
        private int size;
        private String vender;
    }

    /**
     * '블로그 검색서비스' 규격화된 DTO
     */
    @Builder
    @Getter
    public static class BlogResultDto {
        private int totalCount;
        private int pageNum;
        private int pageSize;
        private boolean pageEnd;
        private List<BlogDto.BlogItemtDto> blogItemList;
    }
    /**
     * '블로그 검색서비스' 포스팅 규격화된 DTO
     */
    @Builder
    @Getter
    public static class BlogItemtDto {
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private String datetime;
    }


}
