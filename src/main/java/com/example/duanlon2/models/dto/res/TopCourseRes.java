package com.example.duanlon2.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopCourseRes {
    private Long courseId;
    private String title;
    private Long enrollmentCount;
}

