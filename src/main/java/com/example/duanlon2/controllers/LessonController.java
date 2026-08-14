package com.example.duanlon2.controllers;

import com.example.duanlon2.models.dto.req.LessonPublishReq;
import com.example.duanlon2.models.dto.req.LessonReq;
import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.services.ICourseService;
import com.example.duanlon2.models.services.ILessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final ILessonService lessonService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        log.info("Fetching lesson with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Lesson found successfully")
                        .code(200)
                        .data(lessonService.findByIdPublishedLessons(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable Long id, @ModelAttribute LessonReq req) {
        log.info("Updating lesson with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Lesson updated successfully")
                        .code(200)
                        .data(lessonService.updateLesson(id, req))
                        .build()
        );
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<?> updateLessonPublish(@PathVariable Long id, @ModelAttribute LessonPublishReq req) {
        log.info("Updating lesson publish status with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Lesson publish status updated successfully")
                        .code(200)
                        .data(lessonService.updateLessonPublish(id, req))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        log.info("Deleting lesson with ID: {}", id);
        lessonService.deleteLesson(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Lesson deleted successfully")
                        .code(200)
                        .data(null)
                        .build()
        );
    }
}


