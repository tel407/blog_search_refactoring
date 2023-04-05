package com.devtel.api.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BlogDto {

    /**
     * 블로그 검색 API REQUEST 파라미터
     */
    @Getter
    @Setter
    public static class BlogSearchRequestDto {

        @NotBlank(message = "검색 키워드는 필수 입니다.")
        private String keyword;
        @Min(value = 1 , message = "page 는 1 이상 이여야합니다")
        private Integer page;
        @Min(value = 10 , message = "size 는 10 이상 이여야합니다")
        private Integer size;
        private String sort;
        private String vender;

        public BlogSearchDto toService() {
            return BlogSearchDto.builder()
                    .keyword(this.keyword)
                    .sort(this.sort)
                    .page(this.page)
                    .size(this.size)
                    .vender(this.vender)
                    .build();
        }
    }

    /**
     * 블로그 검색 API Service Dto
     */
    @Getter
    @Builder
    public static class BlogSearchDto {
        private String keyword;
        private String sort;
        private Integer page;
        private Integer size;
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
