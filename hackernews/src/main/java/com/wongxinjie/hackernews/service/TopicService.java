package com.wongxinjie.hackernews.service;

import com.wongxinjie.hackernews.bean.dto.TopicDto;
import com.wongxinjie.hackernews.bean.vo.TopicVo;
import com.wongxinjie.hackernews.entity.Topic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService {

    List<Topic> getAllTopic();
    Page<TopicDto> getTopicByPage(int page, int pageSize);

    TopicDto getTopicById(long topicId);
    long addTopic(TopicVo topicVo, Long userId, int topicType);
    long updateTopic(long topicId, TopicVo topicVo);
    boolean deleteTopic(long topicId, long userId);
}
