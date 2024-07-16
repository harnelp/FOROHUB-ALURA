package com.example.foro.controller;

import com.example.foro.model.Topic;
import com.example.foro.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

  @Autowired
  private TopicService topicService;

  @GetMapping
  public List<Topic> getAllTopics() {
    return topicService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
    Optional<Topic> topic = topicService.findById(id);
    return topic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Topic createTopic(@RequestBody Topic topic) {
    return topicService.save(topic);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
    return topicService.findById(id)
            .map(topic -> {
              topic.setTitle(updatedTopic.getTitle());
              topic.setContent(updatedTopic.getContent());
              return ResponseEntity.ok(topicService.save(topic));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
    return topicService.findById(id)
            .map(topic -> {
              topicService.deleteById(id);
              return ResponseEntity.ok().<Void>build();
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
