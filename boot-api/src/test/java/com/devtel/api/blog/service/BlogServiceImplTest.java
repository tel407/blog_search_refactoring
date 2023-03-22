package com.devtel.api.blog.service;

import com.devtel.api.blog.TestConfigration;
import com.devtel.api.blog.dto.BlogDto;
import com.devtel.api.blog.vender.kakao.KakaoBlogApiDto;
import com.devtel.api.blog.vender.kakao.KakaoSearchBlog;
import com.devtel.api.blog.vender.naver.NaverBlogApiDto;
import com.devtel.api.blog.vender.naver.NaverSearchBlog;
import com.devtel.data.blog.search.service.BlogDataDaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = TestConfigration.class)
@DisplayName("[API 모듈] BlogServiceImpl 테스트")
class BlogServiceImplTest {
    @MockBean
    private BlogDataDaoService blogDataDaoService;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @Autowired
    private KakaoSearchBlog kakaoSearchBlog;

    @Autowired
    private NaverSearchBlog naverSearchBlog;

    BlogDto.BlogSearchDto blogSearchDto;
    @BeforeEach
    void initSetData() {
        blogSearchDto = BlogDto.BlogSearchDto.builder()
                .keyword("의자")
                .page(2)
                .size(100)
                .build();
    }

    @Test
    @DisplayName("카카오 Enum에 따른 최대값 Page 확인")
    void kakaoParamMaxPageCntTest() {
        blogSearchDto = BlogDto.BlogSearchDto.builder()
                .keyword("책상")
                .page(2)
                .size(500)
                .build();
        KakaoBlogApiDto.KakaoBlogApiRequestDto resp = kakaoSearchBlog.convertParamToApiDto(blogSearchDto);
        assertThat(resp).isNotNull();
        assertThat(resp.getPage()).isEqualTo(2);
        assertThat(resp.getSize()).isEqualTo(50);
    }

    @Test
    @DisplayName("네이버 Enum에 따른 최대값 Page 확인")
    void naverParamMaxPageCntTest() {
        blogSearchDto = BlogDto.BlogSearchDto.builder()
                .keyword("그릇")
                .page(200)
                .size(500)
                .build();
        NaverBlogApiDto.NaverBlogApiRequestDto resp = naverSearchBlog.convertParamToApiDto(blogSearchDto);
        assertThat(resp).isNotNull();
        assertThat(resp.getStart()).isEqualTo(100);
        assertThat(resp.getDisplay()).isEqualTo(100);
    }

    @Test
    @DisplayName("Kakao API 연결 및 값 테스트")
    void kakaoWebClientTest() {
        KakaoBlogApiDto.KakaoBlogApiRequestDto requestKaKaoDto = kakaoSearchBlog.convertParamToApiDto(blogSearchDto);
        KakaoBlogApiDto.KakaoBlogApiResponseDto responseKaKaoDto = kakaoSearchBlog.callRequestBlogApi(requestKaKaoDto);

        assertThat(responseKaKaoDto.getDocuments().size()).isEqualTo(requestKaKaoDto.getSize());
    }

    @Test
    @DisplayName("Naver API 연결 및 값 테스트")
    void naverWebClientTest() {
        NaverBlogApiDto.NaverBlogApiRequestDto requestNaverDto = naverSearchBlog.convertParamToApiDto(blogSearchDto);
        NaverBlogApiDto.NaverBlogApiResponseDto responseNaverDto = naverSearchBlog.callRequestBlogApi(requestNaverDto);

        assertThat(responseNaverDto.getItems().size()).isEqualTo(requestNaverDto.getDisplay());
    }

}