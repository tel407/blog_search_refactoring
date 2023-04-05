package com.devtel.api.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

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

    public static List<BlogVenderEnum> getPipe(String venderStr) {
        List<BlogVenderEnum> pipeList = new ArrayList<>();
        for(BlogVenderEnum vender : BlogVenderEnum.values()){
            if(venderStr != null && vender.name.equals(venderStr.toUpperCase())){
                pipeList.add(0,vender);
                continue;
            }
            pipeList.add(vender);
        }
        return pipeList;
    }

}

