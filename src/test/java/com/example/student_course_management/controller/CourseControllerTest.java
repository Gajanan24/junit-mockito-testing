package com.example.student_course_management.controller;

import com.example.student_course_management.dto.CourseResponseDTO;
import com.example.student_course_management.entity.Course;
import com.example.student_course_management.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseControllerTest {
    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;

    private Course sampleCourse;

    private CourseResponseDTO sampleDTOCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCourse = new Course();
        sampleCourse.setCourseId(1L);
        sampleCourse.setTitle("Java Basics");
        sampleCourse.setDescription("Intro to Java");

        sampleDTOCourse = new CourseResponseDTO();
        sampleDTOCourse.setCourseId(1L);
        sampleDTOCourse.setTitle("Java Basics");
        sampleDTOCourse.setDescription("Intro to Java");
    }


    @Test
    void testCreateCourse() {
        when(courseService.createCourse(any(Course.class))).thenReturn(sampleDTOCourse);

        ResponseEntity<CourseResponseDTO> response = courseController.createCourse(sampleCourse);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(sampleDTOCourse.getTitle(), response.getBody().getTitle());
        assertEquals(sampleDTOCourse.getDescription(), response.getBody().getDescription());

        verify(courseService, times(1)).createCourse(sampleCourse);
    }


    @Test
    void testGetAllCourses() {
        CourseResponseDTO course2 = new CourseResponseDTO();
        course2.setCourseId(2L);
        course2.setTitle("DSA");
        course2.setDescription("Data Structures");

        List<CourseResponseDTO> expectedList = Arrays.asList(sampleDTOCourse, course2);

        when(courseService.getAllCourses()).thenReturn(expectedList);

        List<CourseResponseDTO> result = courseController.getAllCourses();

        assertEquals(2, result.size());
        assertEquals("Java Basics", result.get(0).getTitle());
        assertEquals("DSA", result.get(1).getTitle());
        verify(courseService, times(1)).getAllCourses();
    }


}
