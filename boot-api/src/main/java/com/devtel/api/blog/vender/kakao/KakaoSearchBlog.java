package com.devtel.api.blog.vender.kakao;

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

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoSearchBlog implements IVenderBlog<KakaoBlogApiDto.KakaoBlogApiRequestDto, KakaoBlogApiDto.KakaoBlogApiResponseDto>{
    private final WebClient kakaoWebClient;

    public KakaoBlogApiDto.KakaoBlogApiRequestDto convertParamToApiDto(BlogDto.BlogSearchDto blogSearchDto) {

        return KakaoBlogApiDto.KakaoBlogApiRequestDto.builder()
                .query(blogSearchDto.getKeyword().trim())
                .page(Math.min(Math.max((blogSearchDto.getPage() == null)? 1 : blogSearchDto.getPage(), 1), BlogVenderEnum.KAKAO_BLOG.getMaxLimitSize()))
                .size(Math.min(Math.max((blogSearchDto.getSize() == null)? 10 : blogSearchDto.getSize(), 10), BlogVenderEnum.KAKAO_BLOG.getMaxLimitSize()))
                .sort(SortEnum.of(blogSearchDto.getSort()).getKakao()).build();
    }

    public KakaoBlogApiDto.KakaoBlogApiResponseDto callRequestBlogApi(KakaoBlogApiDto.KakaoBlogApiRequestDto kaKaoBlogSearchDto) {
        Mono<KakaoBlogApiDto.KakaoBlogApiResponseDto> mono = kakaoWebClient.get()
                .uri(builder -> builder.path(BlogVenderEnum.KAKAO_BLOG.getUrl())
                        .queryParam("query",kaKaoBlogSearchDto.getQuery())
                        .queryParam("sort",kaKaoBlogSearchDto.getSort())
                        .queryParam("page",kaKaoBlogSearchDto.getPage())
                        .queryParam("size",kaKaoBlogSearchDto.getSize())
                        .build()
                )
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK)){
                        return response.bodyToMono(KakaoBlogApiDto.KakaoBlogApiResponseDto.class);
                    }else{
//                        return Mono.error(ErrorCodeEnum.throwKakaoApiServiceError());
                        return Mono.error(new BlogCustomException(ErrorCodeEnum.BLOG_VENDOR_KAKAO_ERROR));
                    }
                })
                .onErrorResume(error -> {
//                    return Mono.error(ErrorCodeEnum.throwNaverApiServiceError());
                    return Mono.error(new BlogCustomException(ErrorCodeEnum.BLOG_VENDOR_KAKAO_ERROR));
                });
        return mono.block();
    }

    public BlogDto.BlogResultDto getBlogPostingResult(@Valid BlogDto.BlogSearchDto blogSearchDto) {

        BlogDto.BlogResultDto resultDto = null;
        try{
            KakaoBlogApiDto.KakaoBlogApiRequestDto convertParam = convertParamToApiDto(blogSearchDto);
            KakaoBlogApiDto.KakaoBlogApiResponseDto  apiResponseDto = callRequestBlogApi(convertParam);
            resultDto = this.convertResDtoToBlogDto(convertParam, apiResponseDto);
        }catch (Exception e){
            log.error("카카오 BLOG API 호출 ERROR, 다른 VENDER 사 API 를 호출합니다");
        }
        return resultDto;
    }

    public BlogDto.BlogResultDto convertResDtoToBlogDto(KakaoBlogApiDto.KakaoBlogApiRequestDto kaKaoBlogSearchDto, KakaoBlogApiDto.KakaoBlogApiResponseDto kaKaoBlogApiResponseDto) {
        return BlogDto.BlogResultDto.builder()
                .totalCount(kaKaoBlogApiResponseDto.getMeta().getPageableCount())
                .pageNum(kaKaoBlogSearchDto.getPage())
                .pageSize(kaKaoBlogSearchDto.getSize())
                .pageEnd(kaKaoBlogApiResponseDto.getMeta().getIsEnd())
                .blogItemList(kaKaoBlogApiResponseDto.getDocuments().stream().map(item -> {
                    return BlogDto.BlogItemtDto.builder()
                            .title(item.getTitle())
                            .contents(item.getContents())
                            .url(item.getUrl())
                            .thumbnail(item.getThumbnail())
                            .datetime(item.getDatetime())
                            .blogname(item.getBlogname())
                            .build();
                }).collect(Collectors.toList()))
                .build();
    }
}
