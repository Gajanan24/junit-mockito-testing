package com.example.student_course_management.service;

import com.example.student_course_management.dto.CourseResponseDTO;
import com.example.student_course_management.dto.StudentDTO;
import com.example.student_course_management.dto.StudentResponseDTO;
import com.example.student_course_management.entity.Course;
import com.example.student_course_management.entity.Student;
import com.example.student_course_management.repository.CourseRepository;
import com.example.student_course_management.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public StudentResponseDTO createStudent(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setDob(dto.getDob());

        List<Course> courses = courseRepository.findAllById(dto.getCourseIds());

        if(courses.size() != dto.getCourseIds().size()){
            throw  new RuntimeException("Course not found");
        }


        student.setCourses(courses);
        Student student1 = studentRepository.save(student);

        List<CourseResponseDTO> courseResponseDTOS = new ArrayList<>();

        StudentResponseDTO studentResponseDTO =  mapper.map(student1, StudentResponseDTO.class);
        for(Course course : student1.getCourses())
            courseResponseDTOS.add(mapper.map(course, CourseResponseDTO.class));

        studentResponseDTO.setCourses(courseResponseDTOS);
        return studentResponseDTO;

    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
      Student student =  studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student Not Found"));
      return mapper.map(student,StudentResponseDTO.class);
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponseDTO>  studentResponseDTOS = new ArrayList<>();
        for(Student student : students){
            studentResponseDTOS.add(mapper.map(student,StudentResponseDTO.class));
        }
        return studentResponseDTOS;
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
