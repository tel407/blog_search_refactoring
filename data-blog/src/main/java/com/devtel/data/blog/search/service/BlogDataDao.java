package com.devtel.data.blog.search.service;

import com.devtel.data.blog.search.dto.PopularKeywordDataDto;
import com.devtel.data.blog.search.entity.SearchKeywordLogEntity;
import com.devtel.data.blog.search.enums.RedisEnum;
import com.devtel.data.blog.search.repository.PopularKeywordLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogDataDao implements BlogDataDaoService{
    private final PopularKeywordLogRepository popularKeywordRepository;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * [데이터 모듈] 인기검색어 TOP10 가져오기
     */
    @Override
    public List<PopularKeywordDataDto> getPopularKeywordEntityListFromRedis() {
        Set<ZSetOperations.TypedTuple<String>> popularTuplesToRedis = redisTemplate.opsForZSet().reverseRangeWithScores(RedisEnum.BLOG_KEWORD_SCORE.getKey(), 0, 9);
        return popularTuplesToRedis.stream().map(kewordTuple ->
                PopularKeywordDataDto.builder()
                        .keyword(kewordTuple.getValue())
                        .score(Math.round(kewordTuple.getScore()))
                        .build()
        ).collect(Collectors.toList());
    }

    /**
     * [데이터 모듈] REDIS 의 검색어 조회수 증가
     */
    @Override
    public void incrementKeywordScore(String keyword) {
        try {
            redisTemplate.opsForZSet().incrementScore(RedisEnum.BLOG_KEWORD_SCORE.getKey(), keyword, 1);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * [데이터 모듈] 검색어 입력 로그 저장
     */
    @Async
    @Override
    public void saveSearchKeywordLog(String keyword) {
        try {
            popularKeywordRepository.save(SearchKeywordLogEntity.builder()
                            .keyword(keyword)
                            .build());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
