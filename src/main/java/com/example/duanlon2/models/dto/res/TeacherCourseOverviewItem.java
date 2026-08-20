package com.example.duanlon2.models.dto.res;

import com.example.duanlon2.models.constants.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherCourseOverviewItem {
    private Long courseId;
    private String title;
    private CourseStatus status;
    private long enrollmentCount;
}