package com.devtel.api.blog.service;

import com.devtel.api.blog.dto.BlogDto;
import com.devtel.api.blog.enums.BlogVenderEnum;
import com.devtel.api.blog.exception.ErrorCodeEnum;
import com.devtel.api.blog.vender.kakao.KakaoSearchBlog;
import com.devtel.api.blog.vender.naver.NaverSearchBlog;
import com.devtel.data.blog.search.dto.PopularKeywordDataDto;
import com.devtel.data.blog.search.service.BlogDataDaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogDataDaoService blogDataDaoService;//데이터 모듈
    private final KakaoSearchBlog kakaoSearchBlog;      //카카오 Vender 객체
    private final NaverSearchBlog naverSearchBlog;      //네이버 Vender 객체

    /**
     * 키워드 Validation
     */
    private void validParam(BlogDto.BlogSearchDto blogSearchDto) {
        if(blogSearchDto.getKeyword() == null || blogSearchDto.getKeyword().equals("")) {
            throw ErrorCodeEnum.throwEmptyKeywordValid();
        }
    }

    /**
     * 블로그 페이징 및 포스팅 가져오기
     */
    @Override
    public BlogDto.BlogResultDto getBlogSearchByKeyword(BlogDto.BlogSearchDto blogSearchDto) {
        this.validParam(blogSearchDto);
        this.keywordIncrementScore(blogSearchDto.getKeyword());
        //[데이터모듈] 검색어 로그 저장 Async 비동기로 진행 
        blogDataDaoService.saveSearchKeywordLog(blogSearchDto.getKeyword());
        return getBlogPost(blogSearchDto);
    }

    /**
     * Vender 에 의해  검색 엔진 switch 
     * PARAMETER 에 요구 Vender가 있으면 제일 먼저 실행 후 실패시 순서대로 다음 Vender 를 실행한다.
     */
    private BlogDto.BlogResultDto getBlogPost(BlogDto.BlogSearchDto blogSearchDto){
        List<BlogVenderEnum> venderPipe = BlogVenderEnum.getPipe(blogSearchDto.getVender());
        BlogDto.BlogResultDto result = null;
        for(BlogVenderEnum vender : venderPipe){
            if(vender == BlogVenderEnum.KAKAO_BLOG){
                result = kakaoSearchBlog.getBlogPostingResult(blogSearchDto);
            }else if(vender == BlogVenderEnum.NAVER_BLOG){
                result = naverSearchBlog.getBlogPostingResult(blogSearchDto);
            }
            if(result != null) return result;
        }
        throw  ErrorCodeEnum.throwNotConnectionApi();
    }

    /**
     *  인기검색어 가져오기
     */
    @Override
    public List<PopularKeywordDataDto> getBlogPopularTop10ByScore() {
        return blogDataDaoService.getPopularKeywordEntityListFromRedis();
    }

    /**
     *  검색어 임력시 인기검색어 테이블 증가
     */
    @Override
    public void keywordIncrementScore(String keyword) {
        blogDataDaoService.incrementKeywordScore(keyword);
    }

}
