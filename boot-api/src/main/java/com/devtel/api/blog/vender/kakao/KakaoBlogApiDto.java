package com.devtel.api.blog.vender.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class KakaoBlogApiDto {

    @Getter
    @Builder
    public static class KakaoBlogApiRequestDto {
        private String query;
        private String sort;
        private int page;
        private int size;
    }

    @Getter
    public static class KakaoBlogApiResponseDto {
        private KakaoBlogApiDto.KakaoBlogMeta meta;
        private ArrayList<KakaoBlogApiDto.KakaoBlogPostingDto> documents;
    }

    @Getter
    public static class KakaoBlogMeta {
        @JsonProperty("total_count")
        private int totalCount;

        @JsonProperty("pageable_count")
        private int pageableCount;

        @JsonProperty("is_end")
        private Boolean isEnd;
    }

    @Getter
    public static class KakaoBlogPostingDto {
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private String datetime;
    }
}
