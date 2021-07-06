package com.example.forum.controller.form;

import com.example.forum.model.Course;
import com.example.forum.model.Topic;
import com.example.forum.repository.CourseRepository;
import com.example.forum.repository.TopicRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class UpdateTopicForm {
    @NotEmpty
    @NotEmpty @Length(min = 3)
    private String title;

    @NotEmpty @NotEmpty @Length(min = 5)
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<Topic> update(Long id, TopicRepository topicRepository) {
        Optional<Topic> topic = topicRepository.findById(id);

        if (topic.isPresent()) {
            topic.get().setTitle(getTitle());
            topic.get().setMessage(getMessage());
        }

        return topic;
    }
}
