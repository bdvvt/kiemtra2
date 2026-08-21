package com.example.duanlon2.models.services.impl;

import com.example.duanlon2.exceptions.NotFoundException;
import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.dto.req.LessonPublishReq;
import com.example.duanlon2.models.dto.req.LessonReq;
import com.example.duanlon2.models.dto.res.LessonContentPreviewRes;
import com.example.duanlon2.models.entities.Lesson;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.repositories.ILessonRepository;
import com.example.duanlon2.models.services.ILessonService;
import com.example.duanlon2.models.services.uploads.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements ILessonService {
    private static final int CONTENT_PREVIEW_LENGTH = 200;

    private final ILessonRepository lessonRepository;
    private final UploadService uploadService;
    @Override
    public Lesson findByIdPublishedLessons(Long id) {
        return lessonRepository.findByIdWithPublishedLessons(id).orElseThrow(() -> new NotFoundException("Không tìm thấy bài học với ID: " + id));
    }

    @Override
    public LessonContentPreviewRes getContentPreview(Long id) {
        Lesson lesson = findByIdPublishedLessons(id);
        String textContent = lesson.getTextContent();
        String preview = textContent == null ? "" : textContent.trim();
        if (preview.length() > CONTENT_PREVIEW_LENGTH) {
            preview = preview.substring(0, CONTENT_PREVIEW_LENGTH).trim() + "...";
        }

        return LessonContentPreviewRes.builder()
                .lessonId(lesson.getLessonId())
                .title(lesson.getTitle())
                .contentPreview(preview)
                .build();
    }

    @Override
    public Lesson updateLesson(Long id, LessonReq req) {
        Lesson upLesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy bài học với ID: " + id));
        upLesson.setTitle(req.getTitle());
        upLesson.setContentUrl(uploadService.upload(req.getContentUrl()));
        upLesson.setTextContent(req.getTextContent());
        upLesson.setOrderIndex(req.getOrderIndex());
        upLesson.setIsPublished((req.getIsPublished() != null ? req.getIsPublished() : false));
        return lessonRepository.save(upLesson);
    }

    @Override
    public Lesson updateLessonPublish(Long id, LessonPublishReq req) {
        Lesson upLesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy bài học với ID: " + id));
        upLesson.setIsPublished((req.getIsPublished() != null ? req.getIsPublished() : false));
        return lessonRepository.save(upLesson);
    }

    @Override
    public void deleteLesson(Long id) {
        log.info("Deleting lesson record with ID: {}", id);
        Lesson deleteLesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy bài học với ID: " + id));
        lessonRepository.delete(deleteLesson);
    }
}
