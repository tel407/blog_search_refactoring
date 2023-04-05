package com.devtel.api.blog.controller;

import com.devtel.api.blog.dto.BlogDto;
import com.devtel.api.blog.response.ApiSuccessResponse;
import com.devtel.api.blog.service.BlogService;
import com.devtel.data.blog.search.dto.PopularKeywordDataDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "API" ,description = "블로그 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/blog")
public class BlogController {
    private final BlogService blogService;

    /**
     *[키워드 검색 API]
     */
    @GetMapping("/search")
    public ApiSuccessResponse<BlogDto.BlogResultDto> searchBlogByKeyword( @Valid BlogDto.BlogSearchRequestDto blogSearchRequestDto ){
        /*
        * [REFT] Service 레이어와 Controller 레이어의 DTO 를 분리 함으로서 의존관계를 낮춤
        * */
        return ApiSuccessResponse.of(blogService.getBlogSearchByKeyword(blogSearchRequestDto.toService()));
    }

    /**
     *[인기검색 API]
     */
    @GetMapping("/keyword/popular")
    public ApiSuccessResponse<List<PopularKeywordDataDto>> searchBlogPopularKeywordTop10(){
        return ApiSuccessResponse.of(blogService.getBlogPopularTop10ByScore());
    }

}

