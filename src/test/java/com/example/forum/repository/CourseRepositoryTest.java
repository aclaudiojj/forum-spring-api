package com.example.forum.repository;

import com.example.forum.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void findByName() {
        String courseName = "spring boot";

        Course springBoot = new Course();
        springBoot.setName(courseName);

        em.persist(springBoot);

        Course course = repository.findByName(courseName);

        assertNotNull(course);
        assertEquals(courseName, course.getName());
    }

    @Test
    public void findByNameNotExists() {
        String courseName = "any";

        Course course = repository.findByName(courseName);

        assertNull(course);
    }
}