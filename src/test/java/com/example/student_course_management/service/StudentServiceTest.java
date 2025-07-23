package com.example.student_course_management.service;

import com.example.student_course_management.dto.StudentDTO;
import com.example.student_course_management.dto.StudentResponseDTO;
import com.example.student_course_management.entity.Course;
import com.example.student_course_management.entity.Student;
import com.example.student_course_management.repository.CourseRepository;
import com.example.student_course_management.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private ModelMapper mapper;
    private StudentService studentService;

    @BeforeEach
    void setup() {
        studentRepository = mock(StudentRepository.class);
        courseRepository = mock(CourseRepository.class);
        mapper = new ModelMapper(); // real mapper
        studentService = new StudentService();
        studentService.studentRepository = studentRepository;
        studentService.courseRepository = courseRepository;
        studentService.mapper = mapper;
    }

    @Test
    void testCreateStudent_Success() {
        StudentDTO dto = new StudentDTO();
        dto.setName("John");
        dto.setEmail("john@example.com");
        dto.setDob(LocalDate.of(2000, 1, 1));
        dto.setCourseIds(List.of(1L, 2L));

        Course course1 = new Course(1L, "Java", "Core Java", new ArrayList<>());
        Course course2 = new Course(2L, "Spring", "Spring Boot", new ArrayList<>());

        List<Course> courseList = List.of(course1, course2);

        when(courseRepository.findAllById(dto.getCourseIds())).thenReturn(courseList);

        Student savedStudent = new Student(1L, "John", "john@example.com", LocalDate.of(2000, 1, 1), courseList);
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentResponseDTO result = studentService.createStudent(dto);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals(2, result.getCourses().size());
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    void testCreateStudent_CourseNotFound() {
        StudentDTO dto = new StudentDTO();
        dto.setName("John");
        dto.setEmail("john@example.com");
        dto.setDob(LocalDate.of(2000, 1, 1));
        dto.setCourseIds(List.of(1L, 2L));

        when(courseRepository.findAllById(dto.getCourseIds()))
                .thenReturn(List.of(new Course(1L, "Java", "Core", null))); // only one course returned

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            studentService.createStudent(dto);
        });

        assertEquals("Course not found", ex.getMessage());
    }

    @Test
    void testGetStudentById_Success() {
        List<Course> courseList = List.of(new Course(1L, "Java", "Core Java", new ArrayList<>()));
        Student student = new Student(1L, "Jane", "jane@example.com", LocalDate.of(1999, 5, 15), courseList);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentResponseDTO result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Jane", result.getName());
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(100L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            studentService.getStudentById(100L);
        });

        assertEquals("Student Not Found", ex.getMessage());
    }

    @Test
    void testGetAllStudents() {
        List<Student> studentList = List.of(
                new Student(1L, "John", "john@example.com", LocalDate.of(2000, 1, 1), new ArrayList<>()),
                new Student(2L, "Jane", "jane@example.com", LocalDate.of(1999, 5, 15), new ArrayList<>())
        );

        when(studentRepository.findAll()).thenReturn(studentList);

        List<StudentResponseDTO> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
