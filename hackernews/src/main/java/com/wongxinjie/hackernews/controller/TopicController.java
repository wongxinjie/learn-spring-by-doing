package com.wongxinjie.hackernews.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wongxinjie.hackernews.bean.PageResultBean;
import com.wongxinjie.hackernews.bean.ResultBean;
import com.wongxinjie.hackernews.entity.Topic;
import com.wongxinjie.hackernews.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping(value = "topics", produces = "application/json")
    public PageResultBean<Topic> getAllTopic() {
        List<Topic> topics = topicService.getAllTopic();
        PageResultBean<Topic> page = new PageResultBean<>(topics, 1, topics.size(), topics.size(), 1);
        return page;
    }

    @GetMapping(value = "v1.0/topics", params = {"page", "size"}, produces = "application/json")
    public PageResultBean<Topic> getTopicByPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int pageSize) {

        Page<Topic> topicPaginator = topicService.getTopicByPage(page, pageSize);
        return new PageResultBean<>(topicPaginator);
    }

    @GetMapping(value = "v1.0/topics/{id}", produces = "application/json")
    public ResultBean<Topic> getTopicById(@PathVariable("id") Integer id) {
        Topic topic = topicService.getTopicById(id);
        return new ResultBean<>(topic);
    }

    @PostMapping(value = "v1.0/topics", produces = "application/json")
    public ResultBean<Long> createTopic(@RequestBody Topic topic) {
        long topicId = topicService.addTopic(topic);

        ResultBean response = new ResultBean<>(topicId);
        if(topicId == 0) {
            response.setCode(419);
            response.setMessage("topic already exists");
        }
        return response;
    }

    @PutMapping(value = "v1.0/topics/{id}", produces = "application/json")
    public ResultBean<Long> updateTopic(@PathVariable("id") Integer id, @RequestBody Topic topic) {
        long topicId = topicService.updateTopic(id, topic);

        ResultBean response = new ResultBean<>(topicId);
        if(topicId == 0) {
            response.setCode(404);
            response.setMessage("topic not exists");
        }
        return response;
    }

    @DeleteMapping(value="v1.0/topics/{id}", produces = "application/json")
    public ResultBean<Boolean> deleteTopic(@PathVariable("id") Integer id) {
        boolean success = topicService.deleteTopic(id);
        return new ResultBean<>(success);
    }

}
