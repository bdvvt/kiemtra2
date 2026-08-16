package com.example.duanlon2.models.dto.res;

import com.example.duanlon2.models.constants.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StudentCourseProgressQuery {
    private Long enrollmentId;
    private Long courseId;
    private String courseTitle;
    private LocalDateTime enrollmentDate;
    private EnrollmentStatus status;
    private LocalDateTime completionDate;
    private Long totalLessons;
    private Long completedLessons;
    private Double progressPercent;
}

