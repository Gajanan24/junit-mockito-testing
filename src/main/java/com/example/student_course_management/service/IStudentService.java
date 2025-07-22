package com.example.student_course_management.service;

import com.example.student_course_management.dto.StudentDTO;
import com.example.student_course_management.dto.StudentResponseDTO;
import com.example.student_course_management.entity.Student;

import java.util.List;

public interface IStudentService {
    StudentResponseDTO createStudent(StudentDTO student);
    StudentResponseDTO getStudentById(Long id);
    List<StudentResponseDTO> getAllStudents();
    void deleteStudent(Long id);
}
