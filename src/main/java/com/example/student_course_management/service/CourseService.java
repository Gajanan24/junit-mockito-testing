package com.example.student_course_management.service;

import com.example.student_course_management.dto.CourseResponseDTO;
import com.example.student_course_management.entity.Course;
import com.example.student_course_management.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService implements ICourseService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseResponseDTO createCourse(Course course) {
        return mapper.map(courseRepository.save(course),CourseResponseDTO.class);
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses =  courseRepository.findAll();
        List<CourseResponseDTO> courseResponseDTOS = new ArrayList<>();
        for(Course course : courses){
            CourseResponseDTO roomResponseDTO = mapper.map(course, CourseResponseDTO.class);
            courseResponseDTOS.add(roomResponseDTO);
        }
        return courseResponseDTOS;
    }

    @Override
    public CourseResponseDTO getCourseByID(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() ->  new RuntimeException("Course not found"));
        return mapper.map(course, CourseResponseDTO.class);
    }
}
