package com.example.duanlon2.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StudentProgressRes {
    private Long studentId;
    private String username;
    private String email;
    private String fullName;
    private int totalCourses;
    private int completedCourses;
    private BigDecimal averageProgressPercent;
    private List<CourseProgressRes> courses;
}
