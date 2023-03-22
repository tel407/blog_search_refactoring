package com.devtel.data.blog.search.service;

import com.devtel.data.blog.TestConfigration;
import com.devtel.data.blog.search.enums.RedisEnum;
import com.devtel.data.blog.search.repository.PopularKeywordLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = TestConfigration.class)
@DisplayName("[데이터 모듈] BlogDataDao  테스트")
class BlogDataDaoTest {

    @Autowired
    private PopularKeywordLogRepository popularKeywordLogRepository;

    @Autowired
    private BlogDataDao blogDataDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("검색 Score 증가 동시성 테스트")
    public void incrementScoreTest() throws InterruptedException {
        String keyword = "전화기";
        int prevcount = Integer.parseInt(String.valueOf(Math.round(redisTemplate.opsForZSet().score(RedisEnum.BLOG_KEWORD_SCORE.getKey(), keyword))));
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                System.out.println("----- redis inc ----");
                blogDataDao.incrementKeywordScore(keyword);
                latch.countDown();
            });
        }
        latch.await();

        assertThat(redisTemplate.opsForZSet().score(RedisEnum.BLOG_KEWORD_SCORE.getKey(), keyword)).isEqualTo(prevcount + numberOfThreads);
    }

    @Test
    @DisplayName("키워드 검색 로그 Async 동시성 테스트")
    public void saveLogTest() throws InterruptedException {
        String keyword = "전화기";
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                System.out.println("----- save log ----");
                blogDataDao.saveSearchKeywordLog(keyword);
                latch.countDown();
            });
        }
        latch.await();

        long logCount = popularKeywordLogRepository.countBy();
        assertThat(numberOfThreads).isEqualTo(logCount);
    }
}