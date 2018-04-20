package com.wongxinjie.hackernews.controller;

import com.wongxinjie.hackernews.bean.PageResultBean;
import com.wongxinjie.hackernews.bean.ResultBean;
import com.wongxinjie.hackernews.bean.dto.TopicDto;
import com.wongxinjie.hackernews.bean.vo.TopicVo;
import com.wongxinjie.hackernews.entity.Topic;
import com.wongxinjie.hackernews.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<PageResultBean<Topic>> getAllTopic() {
        List<Topic> topics = topicService.getAllTopic();
        PageResultBean<Topic> page = new PageResultBean<>(topics, 1, topics.size(), topics.size(), 1);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "v1.0/topics", params = {"page", "size"}, produces = "application/json")
    public ResponseEntity<PageResultBean<TopicDto>> getTopicByPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int pageSize) {

        Page<TopicDto> topicPaginator = topicService.getTopicByPage(page, pageSize);
        PageResultBean<TopicDto> pages = new PageResultBean<>(topicPaginator);
        return ResponseEntity.ok(pages);
    }

    @GetMapping(value = "v1.0/topics/{id}", produces = "application/json")
    public ResponseEntity<ResultBean<TopicDto>> getTopicById(@PathVariable("id") Integer id) {
        TopicDto topicDto = topicService.getTopicById(id);
        return ResponseEntity.ok(new ResultBean<>(topicDto));
    }

    @PostMapping(value = "v1.0/topics", produces = "application/json")
    public ResponseEntity<ResultBean<Long>> createTopic(@RequestBody TopicVo topicVo, Authentication authentication) {
        long topicId = topicService.addTopic(topicVo);

        ResultBean<Long> response = new ResultBean<>(topicId);
        if(topicId == 0) {
            response.setCode(419);
            response.setMessage("topic already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "v1.0/topics/{id}", produces = "application/json")
    public ResponseEntity<ResultBean<Long>> updateTopic(@PathVariable("id") Integer id, @RequestBody Topic topic) {
        long topicId = topicService.updateTopic(id, topic);

        ResultBean<Long> response = new ResultBean<>(topicId);
        if(topicId == 0) {
            response.setCode(404);
            response.setMessage("topic not exists");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value="v1.0/topics/{id}", produces = "application/json")
    public ResponseEntity<ResultBean<Boolean>> deleteTopic(@PathVariable("id") Integer id) {
        boolean success = topicService.deleteTopic(id);
        return ResponseEntity.ok(new ResultBean<>(success));
    }

}
