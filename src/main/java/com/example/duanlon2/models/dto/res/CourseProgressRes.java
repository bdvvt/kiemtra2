package com.example.duanlon2.models.dto.res;

import com.example.duanlon2.models.constants.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CourseProgressRes {
    private Long enrollmentId;
    private Long courseId;
    private String courseTitle;
    private LocalDateTime enrollmentDate;
    private EnrollmentStatus status;
    private BigDecimal progressPercent;
    private long completedLessons;
    private long totalLessons;
    private LocalDateTime completionDate;
}

