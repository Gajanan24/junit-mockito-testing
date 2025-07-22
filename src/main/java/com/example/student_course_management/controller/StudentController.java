package com.example.student_course_management.controller;

import com.example.student_course_management.dto.StudentDTO;
import com.example.student_course_management.dto.StudentResponseDTO;
import com.example.student_course_management.entity.Course;
import com.example.student_course_management.entity.Student;
import com.example.student_course_management.service.CourseService;
import com.example.student_course_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/check")
    public String hello(){
        return "hello World";
    }

    @PostMapping()
    public StudentResponseDTO createStudent(@RequestBody StudentDTO studentDTO){
        return studentService.createStudent(studentDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping
    public List<StudentResponseDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
