package com.devtel.data.blog.search.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisEnum {
    BLOG_KEWORD_SCORE("devtel_keyword_socre")
    ;

    private final String key ;
}

