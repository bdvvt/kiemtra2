package com.example.duanlon2.models.dto.res;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopCourseRes {
    private Long courseId;
    private String title;
    private Long enrollmentCount;
}
