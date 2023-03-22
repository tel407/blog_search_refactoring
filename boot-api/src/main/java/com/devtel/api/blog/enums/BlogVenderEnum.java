package com.devtel.api.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlogVenderEnum {
    KAKAO_BLOG("KAKAO","카카오블로그","/v2/search/blog", 50),
    NAVER_BLOG("NAVER","네이버블로그","/v1/search/blog.json",100)
    ;

    private final String name ;
    private final String title ;
    private final String url ;
    private final int maxLimitSize ;

    public static BlogVenderEnum of(String venderStr) {
        for(BlogVenderEnum vender : BlogVenderEnum.values()){
            if(vender.name.equals(venderStr.toUpperCase())){
                return vender;
            }
        }
        return BlogVenderEnum.KAKAO_BLOG;
    }
}

