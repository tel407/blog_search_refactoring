package com.devtel.api.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@Getter
@AllArgsConstructor
public enum SortEnum {
    RECENCY("최신순","recency","sim"),
    ACCURACY("정확도순","accuracy","date")
    ;

    private final String title ;
    private final String kakao ;
    private final String naver ;

    public static SortEnum of(String sortStr) {
        for(SortEnum sort : SortEnum.values()){
            if(sort.kakao.equals(sortStr.toLowerCase()) || sort.naver.equals(sortStr.toLowerCase())){
                return sort;
            }
        }
        return SortEnum.ACCURACY;
    }

}

