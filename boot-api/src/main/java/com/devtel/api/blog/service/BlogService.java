package com.devtel.api.blog.service;

import com.devtel.api.blog.dto.BlogDto;
import com.devtel.data.blog.search.dto.PopularKeywordDataDto;

import java.util.List;

public interface BlogService {
    BlogDto.BlogResultDto getBlogSearchByKeyword(BlogDto.BlogSearchDto blogSearchDto);
    List<PopularKeywordDataDto> getBlogPopularTop10ByScore();
    void keywordIncrementScore(String keyword);
}
