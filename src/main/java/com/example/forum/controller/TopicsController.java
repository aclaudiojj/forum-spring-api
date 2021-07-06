package com.example.forum.controller;

import com.example.forum.controller.dto.TopicDetailDto;
import com.example.forum.controller.dto.TopicDto;
import com.example.forum.controller.form.TopicForm;
import com.example.forum.controller.form.UpdateTopicForm;
import com.example.forum.model.Topic;
import com.example.forum.repository.CourseRepository;
import com.example.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicsController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    @Cacheable(value = "topicList")
    public Page<TopicDto> list(
            @RequestParam(required = false) String courseName,
            @PageableDefault(
                    sort = "id",
                    direction = Sort.Direction.DESC,
                    page = 0,
                    size = 10
            ) Pageable pagination
    ) {
        if (courseName == null) {
            Page<Topic> topics = topicRepository.findAll(pagination);

            return TopicDto.topics(topics);
        }

        Page<Topic> topics = topicRepository.findByCourseName(courseName, pagination);

        return TopicDto.topics(topics);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "topicList", allEntries = true)
    public ResponseEntity<TopicDto> add(@RequestBody @Valid TopicForm topicForm, UriComponentsBuilder builder) {
        Topic topic = topicForm.topic(courseRepository);

        topicRepository.save(topic);

        URI uri = builder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();

        TopicDto topicDto = new TopicDto(topic);

        return ResponseEntity.created(uri).body(topicDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailDto> detail(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);

        return topic.map(
                value -> ResponseEntity.ok(new TopicDetailDto(value))
        ).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "topicList", allEntries = true)
    public ResponseEntity<TopicDto> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicForm updateTopicForm) {
        Optional<Topic> topic = updateTopicForm.update(id, topicRepository);

        return topic.map(
                value -> ResponseEntity.ok(new TopicDto(topic.get()))
        ).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "topicList", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);

        return topic.map(
                value -> {
                    topicRepository.deleteById(id);

                    return ResponseEntity.ok().build();
                }
        ).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }
}
