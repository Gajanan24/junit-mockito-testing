package com.example.student_course_management.service;

import com.example.student_course_management.dto.CourseResponseDTO;
import com.example.student_course_management.entity.Course;

import java.util.List;

public interface ICourseService {
    CourseResponseDTO createCourse(Course course);
    List<CourseResponseDTO> getAllCourses();
    CourseResponseDTO getCourseByID(Long id);
}
