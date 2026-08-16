package com.example.duanlon2.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TeacherCoursesOverviewRes {
    private Long teacherId;
    private String teacherName;
    private String email;
    private int totalCourses;
    private int publishedCourses;
    private long totalEnrollments;
    private BigDecimal averageProgressPercent;
    private List<TeacherCourseOverviewItem> courses;
}

