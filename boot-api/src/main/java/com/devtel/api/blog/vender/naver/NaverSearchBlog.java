package com.devtel.api.blog.vender.naver;

import com.devtel.api.blog.config.exception.BlogCustomException;
import com.devtel.api.blog.dto.BlogDto;
import com.devtel.api.blog.enums.BlogVenderEnum;
import com.devtel.api.blog.enums.SortEnum;
import com.devtel.api.blog.config.exception.ErrorCodeEnum;
import com.devtel.api.blog.vender.IVenderBlog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverSearchBlog implements IVenderBlog<NaverBlogApiDto.NaverBlogApiRequestDto, NaverBlogApiDto.NaverBlogApiResponseDto> {
    private final WebClient naverWebClient;

    public NaverBlogApiDto.NaverBlogApiRequestDto convertParamToApiDto(BlogDto.BlogSearchDto blogSearchDto) {
        int pageSize = Math.max((blogSearchDto.getSize() == null)? 1 : blogSearchDto.getSize(), 10);
        int pageStart = (Math.max((blogSearchDto.getPage() == null)? 1 : blogSearchDto.getPage(), 1) -1) * pageSize + 1;
        return NaverBlogApiDto.NaverBlogApiRequestDto.builder()
                .query(blogSearchDto.getKeyword().trim())
                .start(Math.min(pageStart, BlogVenderEnum.NAVER_BLOG.getMaxLimitSize()))
                .display(Math.min(pageSize, BlogVenderEnum.NAVER_BLOG.getMaxLimitSize()))
                .sort(SortEnum.of(blogSearchDto.getSort()).getNaver()).build();
    }

    public NaverBlogApiDto.NaverBlogApiResponseDto callRequestBlogApi(NaverBlogApiDto.NaverBlogApiRequestDto naverBlogSearchDto) {
        Mono<NaverBlogApiDto.NaverBlogApiResponseDto> mono = naverWebClient.get()
                .uri(builder -> builder.path(BlogVenderEnum.NAVER_BLOG.getUrl())
                        .queryParam("query",naverBlogSearchDto.getQuery())
                        .queryParam("sort",naverBlogSearchDto.getSort())
                        .queryParam("start",naverBlogSearchDto.getStart())
                        .queryParam("display",naverBlogSearchDto.getDisplay())
                        .build()
                )
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK)){
                        return response.bodyToMono(NaverBlogApiDto.NaverBlogApiResponseDto.class);
                    }else{
//                        return Mono.error(ErrorCodeEnum.throwNaverApiServiceError());
                        return Mono.error(new BlogCustomException(ErrorCodeEnum.BLOG_VENDOR_NAVER_ERROR));
                    }
                })
                .onErrorResume(error -> {
//                    return Mono.error(ErrorCodeEnum.throwNaverApiServiceError());
                    return Mono.error(new BlogCustomException(ErrorCodeEnum.BLOG_VENDOR_NAVER_ERROR));
                });
        return mono.block();
    }

    public BlogDto.BlogResultDto getBlogPostingResult(BlogDto.BlogSearchDto blogSearchDto) {
        BlogDto.BlogResultDto resultDto = null;
        try{
            NaverBlogApiDto.NaverBlogApiRequestDto convertParam = convertParamToApiDto(blogSearchDto);
            NaverBlogApiDto.NaverBlogApiResponseDto  apiResponseDto = callRequestBlogApi(convertParam);
            resultDto = this.convertResDtoToBlogDto(convertParam, apiResponseDto);
        }catch (Exception e){
            log.error("네이버 BLOG API 호출 ERROR, 다른 VENDER 사 API 를 호출합니다");
        }
        return resultDto;
    }

    public BlogDto.BlogResultDto convertResDtoToBlogDto(NaverBlogApiDto.NaverBlogApiRequestDto naverBlogApiRequestDto, NaverBlogApiDto.NaverBlogApiResponseDto naverBlogApiResponseDto) {
        return BlogDto.BlogResultDto.builder()
                .totalCount(naverBlogApiResponseDto.getTotal())
                .pageNum((int) Math.ceil((double)naverBlogApiResponseDto.getStart() / naverBlogApiResponseDto.getDisplay()))
                .pageSize(naverBlogApiResponseDto.getDisplay())
                .pageEnd((naverBlogApiResponseDto.getTotal() <= (naverBlogApiResponseDto.getStart() * naverBlogApiResponseDto.getDisplay())))
                .blogItemList(naverBlogApiResponseDto.getItems().stream().map(item -> {
                    return BlogDto.BlogItemtDto.builder()
                            .title(item.getTitle())
                            .contents(item.getDescription())
                            .url(item.getLink())
                            .thumbnail("")
                            .datetime(item.getPostdate())
                            .blogname(item.getBloggername())
                            .build();
                }).collect(Collectors.toList()))
                .build();
    }
}
