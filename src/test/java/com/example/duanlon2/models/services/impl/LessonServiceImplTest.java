package com.example.duanlon2.models.services.impl;

import com.example.duanlon2.exceptions.NotFoundException;
import com.example.duanlon2.models.dto.res.LessonContentPreviewRes;
import com.example.duanlon2.models.entities.Lesson;
import com.example.duanlon2.models.repositories.ILessonRepository;
import com.example.duanlon2.models.services.uploads.UploadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock
    private ILessonRepository lessonRepository;

    @Mock
    private UploadService uploadService;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void getContentPreviewReturnsTrimmedContentAndEllipsisWhenContentExceedsLimit() {
        String content = "a".repeat(250);
        Lesson lesson = Lesson.builder()
                .lessonId(12L)
                .title("Java Basics")
                .textContent("  " + content + "  ")
                .isPublished(true)
                .build();
        when(lessonRepository.findByIdWithPublishedLessons(12L)).thenReturn(Optional.of(lesson));

        LessonContentPreviewRes result = lessonService.getContentPreview(12L);

        assertThat(result.getLessonId()).isEqualTo(12L);
        assertThat(result.getTitle()).isEqualTo("Java Basics");
        assertThat(result.getContentPreview()).isEqualTo("a".repeat(200) + "...");
    }

    @Test
    void getContentPreviewThrowsWhenLessonIsNotPublishedOrDoesNotExist() {
        when(lessonRepository.findByIdWithPublishedLessons(12L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> lessonService.getContentPreview(12L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Không tìm thấy bài học với ID: 12");
    }
}
