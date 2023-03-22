package com.devtel.api.blog.vender.naver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class NaverBlogApiDto {

    @Getter
    @Builder
    public static class NaverBlogApiRequestDto {
        private String query;
        private String sort;
        private int display;
        private int start;
    }

    @Getter
    public static class NaverBlogApiResponseDto {
        private String lastBuildDate;
        private int total;
        private int start;
        private int display;
        private List<NaverBlogPostingDto> items;
    }

    @Getter
    public static class NaverBlogPostingDto {
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private String postdate;
    }
}
