package com.devtel.data.blog.search.repository;

import com.devtel.data.blog.search.entity.SearchKeywordLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularKeywordLogRepository extends JpaRepository<SearchKeywordLogEntity, Long> {
    Long countBy();
}
