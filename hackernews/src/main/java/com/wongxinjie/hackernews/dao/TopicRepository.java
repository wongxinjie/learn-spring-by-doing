package com.wongxinjie.hackernews.dao;

import com.wongxinjie.hackernews.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByTitle(String title);
    List<Topic> findByCreatedBy(Long userId);
    List<Topic> findByUrl(String url);
}
