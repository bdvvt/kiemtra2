package com.example.duanlon2.models.dto.res;

import com.example.duanlon2.models.constants.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class TeacherCourseOverviewItem {
    private Long courseId;
    private String title;
    private CourseStatus status;
    private long enrollmentCount;
    private BigDecimal averageProgressPercent;
}

