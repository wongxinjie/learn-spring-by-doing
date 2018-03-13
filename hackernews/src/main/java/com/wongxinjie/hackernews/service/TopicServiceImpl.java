package com.wongxinjie.hackernews.service;

import com.wongxinjie.hackernews.dao.TopicRepository;
import com.wongxinjie.hackernews.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    private TopicRepository topicDao;

    @Override
    public List<Topic> getAllTopic() {
        List<Topic> topics = new ArrayList<>();
        topicDao.findAll().forEach(e -> topics.add(e));
        return topics;
    }

    @Override
    public Page<Topic> getTopicByPage(int page, int pageSize) {
        return topicDao.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Topic getTopicById(long topicId) {
        Topic topic = topicDao.findById(topicId).get();
        return topic;
    }

    @Override
    public synchronized long addTopic(Topic topic) {
        List<Topic> list = topicDao.findByUrl(topic.getUrl());
        if(list.size() > 0) {
            return 0;
        }

        topicDao.save(topic);
        return  topic.getId();
    }

    @Override
    public long updateTopic(long topicId, Topic topic) {
        Topic t = topicDao.findById(topicId).get();
        if(t == null) {
            return 0;
        }

        topic.setId(topicId);
        topicDao.save(topic);
        return topicId;
    }

    @Override
    public void deleteTopic(long topicId) {
        Topic topic = topicDao.getOne(topicId);
        if(topic != null) {
            topicDao.delete(topic);
        }
    }
}
