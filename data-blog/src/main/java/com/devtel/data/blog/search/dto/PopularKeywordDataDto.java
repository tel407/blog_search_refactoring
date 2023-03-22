package com.devtel.data.blog.search.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PopularKeywordDataDto {
    /**
     * 인기검색어 DTO
     */
    private String keyword;
    private Long score;

    @Builder
    public PopularKeywordDataDto(String keyword, Long score) {
        this.keyword = keyword;
        this.score = score;
    }

}
