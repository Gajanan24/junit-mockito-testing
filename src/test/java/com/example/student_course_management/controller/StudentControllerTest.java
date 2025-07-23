package com.example.student_course_management.controller;

import com.example.student_course_management.dto.StudentDTO;
import com.example.student_course_management.dto.StudentResponseDTO;
import com.example.student_course_management.service.CourseService;
import com.example.student_course_management.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    private StudentDTO studentDTO;
    private StudentResponseDTO studentResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        studentDTO = new StudentDTO();
        studentDTO.setName("John");
        studentDTO.setEmail("john@example.com");
        studentDTO.setDob(LocalDate.of(2000, 1, 1));
        studentDTO.setCourseIds(List.of(1L, 2L));

        studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setStudentId(1L);
        studentResponseDTO.setName("John");
        studentResponseDTO.setEmail("john@example.com");
        studentResponseDTO.setDob(LocalDate.of(2000, 1, 1));
    }

    @Test
    void testHelloEndpoint() {
        String result = studentController.hello();
        assertEquals("hello World", result);
    }

    @Test
    void testCreateStudent() {
        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(studentResponseDTO);

        StudentResponseDTO result = studentController.createStudent(studentDTO);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(studentService, times(1)).createStudent(any(StudentDTO.class));
    }

    @Test
    void testGetStudentById() {
        when(studentService.getStudentById(1L)).thenReturn(studentResponseDTO);

        ResponseEntity<StudentResponseDTO> response = studentController.getStudent(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("john@example.com", response.getBody().getEmail());
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testGetAllStudents() {
        StudentResponseDTO secondStudent = new StudentResponseDTO();
        secondStudent.setStudentId(2L);
        secondStudent.setName("Alice");
        secondStudent.setEmail("alice@example.com");
        secondStudent.setDob(LocalDate.of(1999, 5, 20));

        when(studentService.getAllStudents()).thenReturn(Arrays.asList(studentResponseDTO, secondStudent));

        List<StudentResponseDTO> result = studentController.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Alice", result.get(1).getName());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentService).deleteStudent(1L);

        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(studentService, times(1)).deleteStudent(1L);
    }
}
