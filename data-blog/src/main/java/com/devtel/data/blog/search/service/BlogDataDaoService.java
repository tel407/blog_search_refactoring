package com.devtel.data.blog.search.service;

import com.devtel.data.blog.search.dto.PopularKeywordDataDto;

import java.util.List;

public interface BlogDataDaoService {
    List<PopularKeywordDataDto> getPopularKeywordEntityListFromRedis();
    void incrementKeywordScore(String keyword);
    void saveSearchKeywordLog(String keyword);
}
