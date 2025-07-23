package com.example.student_course_management.service;

import com.example.student_course_management.dto.CourseResponseDTO;
import com.example.student_course_management.entity.Course;
import com.example.student_course_management.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCreateCourse() {
//        Course course = new Course();
//        course.setTitle("Java");
//        course.setDescription("Core Java");
//
//        when(courseRepository.save(any(Course.class))).thenReturn(course);
//
//        CourseResponseDTO savedCourse = courseService.createCourse(course);
//
//        assertNotNull(savedCourse);
//        assertEquals("Java", savedCourse.getTitle());
//        verify(courseRepository, times(1)).save(course);
//    }

    @Test
    void testCreateCourse() {
        Course course = new Course();
        course.setTitle("Java");
        course.setDescription("Core Java");

        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setTitle("Java");
        dto.setDescription("Core Java");

        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(mapper.map(course, CourseResponseDTO.class)).thenReturn(dto); // mock mapping

        CourseResponseDTO savedCourse = courseService.createCourse(course);

        assertNotNull(savedCourse);
        assertEquals("Java", savedCourse.getTitle());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = Arrays.asList(
                new Course(1L, "Java", "Basics", null),
                new Course(2L, "Spring Boot", "Advanced", null)
        );

        when(courseRepository.findAll()).thenReturn(courses);

        List<CourseResponseDTO> result = courseService.getAllCourses();

        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById_Found() {
        Course course = new Course(1L, "Java", "Basics", null);
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setTitle("Java");
        dto.setDescription("Basics");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(mapper.map(course, CourseResponseDTO.class)).thenReturn(dto);

        CourseResponseDTO found = courseService.getCourseByID(1L);

        assertNotNull(found);
        assertEquals("Java", found.getTitle());
    }


    @Test
    void testGetCourseById_NotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            courseService.getCourseByID(99L);
        });

        assertEquals("Course not found", ex.getMessage());
    }




}
