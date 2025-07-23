package com.example.student_course_management.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String name;
    private String email;
    private LocalDate dob;
    private List<Long> courseIds;
}
