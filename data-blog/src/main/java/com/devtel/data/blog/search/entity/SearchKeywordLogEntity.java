package com.devtel.data.blog.search.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="TBL_SEARCH_KEYWORD_LOG")
public class SearchKeywordLogEntity{
    /**
     *  REDIS 만으로 부족해 백업용 DB 대체하고자 LOG 쌓음
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Builder
    public SearchKeywordLogEntity(String keyword){
        this.keyword = keyword;
    }
}
