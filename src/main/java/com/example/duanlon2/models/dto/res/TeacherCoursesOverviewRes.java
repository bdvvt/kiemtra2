package com.example.duanlon2.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherCoursesOverviewRes {
    private Long teacherId;
    private String teacherName;
    private String email;
    private int totalCourses;
    private int publishedCourses;
    private long totalEnrollments;
    private List<TeacherCourseOverviewItem> courses;
}
