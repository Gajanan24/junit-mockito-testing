package com.example.student_course_management.dto;

import com.example.student_course_management.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private Long studentId;
    private String name;
    private String email;
    private LocalDate dob;
    private List<CourseResponseDTO> courses;
}
