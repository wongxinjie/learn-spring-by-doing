package com.wongxinjie.hackernews.controller;

import com.wongxinjie.hackernews.Common.Result;
import com.wongxinjie.hackernews.entity.Topic;
import com.wongxinjie.hackernews.exception.ResourceNotFoundException;
import com.wongxinjie.hackernews.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping(value = "/topics", produces = "application/json")
    public ResponseEntity<List<Topic>> getAllTopic() {
        List<Topic> topics = topicService.getAllTopic();
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @GetMapping(value = "/v1.0/topics", params = {"page", "size"}, produces = "application/json")
    public Page<Topic> getTopicByPage(@RequestParam("page") int page, @RequestParam("size") int pageSize) {
        Page<Topic> topicPage = topicService.getTopicByPage(page, pageSize);
        if(page > topicPage.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return topicPage;
    }

    @GetMapping(value = "/v1.0/topics/{id}", produces = "application/json")
    public ResponseEntity<Topic> getTopicById(@PathVariable("id") Integer id) {
        Topic topic = topicService.getTopicById(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @PostMapping(value = "/v1.0/topics", produces = "application/json")
    public ResponseEntity<Result> createTopic(@RequestBody Topic topic) {
        long topicId = topicService.addTopic(topic);
        Result response = new Result();
        HttpStatus status = HttpStatus.CREATED;
        if(topicId == 0) {
            response.setCode(419);
            response.setMessage("topic already exists");
            status = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(response, status);
    }

    @PutMapping(value = "/v1.0/topics/{id}", produces = "application/json")
    public ResponseEntity<Result> updateTopic(@PathVariable("id") Integer id, @RequestBody Topic topic) {
        long topicId = topicService.updateTopic(id, topic);

        Result response = new Result();
        HttpStatus status = HttpStatus.OK;
        if(topicId == 0) {
            response.setCode(404);
            response.setMessage("topic not exists");
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping(value="/v1.0/topics/{id}", produces = "application/json")
    public ResponseEntity<Result> deleteTopic(@PathVariable("id") Integer id) {
        topicService.deleteTopic(id);
        return new ResponseEntity<>(new Result(), HttpStatus.OK);
    }

}
