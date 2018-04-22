package com.wongxinjie.hackernews.service.impl;

import com.wongxinjie.hackernews.bean.dto.TopicDto;
import com.wongxinjie.hackernews.bean.vo.TopicVo;
import com.wongxinjie.hackernews.common.NetworkUtils;
import com.wongxinjie.hackernews.common.TimeUtils;
import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.repository.TopicRepository;
import com.wongxinjie.hackernews.entity.Topic;
import com.wongxinjie.hackernews.repository.UserRepository;
import com.wongxinjie.hackernews.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private static Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Topic> getAllTopic() {
        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll().forEach(e -> topics.add(e));
        return topics;
    }

    @Override
    public Page<TopicDto> getTopicByPage(int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        page = page - 1;

        Pageable pageRequest = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdAt");
        Page<Topic> paginator = topicRepository.findAll(pageRequest);

        List<Topic> topics = paginator.getContent().stream().collect(Collectors.toList());
        List<TopicDto> topicDtos = new ArrayList<>();
        for (Topic t : topics) {
            topicDtos.add(mapEntityToDto(t));
        }

        return new PageImpl<>(topicDtos, pageRequest, paginator.getTotalElements());
    }

    @Override
    public TopicDto getTopicById(long topicId) {
        Topic topic = topicRepository.findById(topicId).get();
        return mapEntityToDto(topic);
    }

    @Override
    public long addTopic(TopicVo topicVo, Long userId, int topicType) {
        List<Topic> list = topicRepository.findByUrl(topicVo.getUrl());
        if(list.size() > 0) {
            return 0;
        }
        Optional<User> optional = userRepository.findById(userId);

        Topic topic = new Topic();
        topic.setTopicType(topicType);
        topic.setTitle(topicVo.getTitle());
        topic.setUrl(topicVo.getUrl());
        topic.setUser(optional.get());
        topic.setRedirect(topicVo.getRedirect());
        topic.setDomain(NetworkUtils.getDomainFromUrl(topicVo.getUrl()));
        topicRepository.save(topic);

        log.info("User {} create topic with url {}", userId, topic.getUrl());
        return  topic.getId();
    }

    @Override
    public long updateTopic(long topicId, TopicVo topicVo, long userId) {
        Optional<Topic> optional = topicRepository.findById(topicId);
        if (!optional.isPresent()) {
            return 0;
        }

        Topic topic = optional.get();
        if (topic.getUser().getId() != userId) {
            //should raise exception
            return 0;
        }

        topic.setTitle(topicVo.getTitle());
        topic.setUrl(topicVo.getUrl());
        topic.setRedirect(topicVo.getRedirect());
        topic.setId(topicId);
        topicRepository.save(topic);

        return topicId;
    }

    @Override
    public boolean deleteTopic(long topicId, long userId) {
        Optional<Topic> optional = topicRepository.findById(topicId);
        if (!optional.isPresent()) {
            return false;
        }

        Topic topic = optional.get();
        if(topic != null && topic.getUser().getId() != userId) {
            topicRepository.delete(topic);
            log.info("User {} remove topic id {}", userId, topicId);
            return true;
        }
        return false;
    }

    private TopicDto mapEntityToDto(Topic entity) {
        TopicDto dto = new TopicDto();
        dto.setTitle(entity.getTitle());
        dto.setDomain(entity.getDomain());
        dto.setPoints(0);
        dto.setRedirect(entity.getRedirect());
        dto.setWhen(TimeUtils.localTimeText(entity.getCreatedAt()));
        dto.setPoster(entity.getUser().getUsername());
        return dto;
    }
}
