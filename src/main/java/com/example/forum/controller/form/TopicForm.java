package com.example.forum.controller.form;

import com.example.forum.model.Course;
import com.example.forum.model.Topic;
import com.example.forum.repository.CourseRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class TopicForm {
    @NotEmpty @NotEmpty @Length(min = 3)
    private String title;

    @NotEmpty @NotEmpty @Length(min = 5)
    private String message;

    @NotEmpty @NotEmpty
    private String courseName;

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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Topic topic(CourseRepository courseRepository) {
        Course course = courseRepository.findByName(getCourseName());

        return new Topic(getTitle(), getMessage(), course);
    }
}
