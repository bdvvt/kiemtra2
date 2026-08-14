package com.example.duanlon2.models.services.impl;

import com.example.duanlon2.exceptions.NotFoundException;
import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.dto.req.LessonPublishReq;
import com.example.duanlon2.models.dto.req.LessonReq;
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
    private final ILessonRepository lessonRepository;
    private final UploadService uploadService;
    @Override
    public Lesson findByIdPublishedLessons(Long id) {
        return lessonRepository.findByIdWithPublishedLessons(id).orElseThrow(() -> new NotFoundException("Không tìm thấy bài học với ID: " + id));
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
