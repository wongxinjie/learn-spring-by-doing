package com.wongxinjie.hackernews.service;

import com.wongxinjie.hackernews.entity.Topic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService {

    List<Topic> getAllTopic();
    Page<Topic> getTopicByPage(int page, int pageSize);

    Topic getTopicById(long topicId);
    long addTopic(Topic topic);
    long updateTopic(long topicId, Topic topic);
    boolean deleteTopic(long topicId);
}
