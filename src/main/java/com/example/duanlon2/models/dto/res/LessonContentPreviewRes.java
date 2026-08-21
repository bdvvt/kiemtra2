package com.example.duanlon2.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LessonContentPreviewRes {
    private Long lessonId;
    private String title;
    private String contentPreview;
}

// end
