package com.example.duanlon2.controllers;

import com.example.duanlon2.models.dto.req.EnrollmentReq;
import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.services.IEnrollmentService;
import com.example.duanlon2.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<?> getStudentEnrollments(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long studentId = userDetails.getUser().getId();
        log.info("Request to fetch enrolled courses for logged-in student ID: {}", studentId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Enrolled Courses Successfully")
                        .code(200)
                        .data(enrollmentService.getStudentEnrollments(studentId))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> enrollCourse(@Valid @ModelAttribute EnrollmentReq req, @AuthenticationPrincipal CustomUserDetails userDetails ) {
        Long studentId = userDetails.getUser().getId();
        log.info("Request to enroll course ID: {} by student ID: {}", req.getCourseId(), studentId);
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Enroll Course Successfully")
                        .code(201)
                        .data(enrollmentService.enrollCourse(req, studentId))
                        .build()
        );
    }

    @GetMapping("/{enrollment_id}")
    public ResponseEntity<?> getEnrollmentDetail(@PathVariable("enrollment_id") Long enrollmentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long studentId = userDetails.getUser().getId();
        log.info("Request to fetch enrollment detail ID: {} for logged-in student ID: {}", enrollmentId, studentId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Enrollment Detail Successfully")
                        .code(200)
                        .data(enrollmentService.getEnrollmentDetail(enrollmentId, studentId))
                        .build()
        );
    }

    @PutMapping("/{enrollment_id}/complete_lesson/{lesson_id}")
    public ResponseEntity<?> completeLessonForEnrollment(@PathVariable("enrollment_id") Long enrollmentId,@PathVariable("lesson_id") Long lessonId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long studentId = userDetails.getUser().getId();
        log.info("Request to mark lesson ID: {} as completed for enrollment ID: {} by student ID: {}", lessonId, enrollmentId, studentId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Mark Lesson Completed Successfully")
                        .code(200)
                        .data(enrollmentService.completeLesson(enrollmentId, lessonId, studentId))
                        .build()
        );
    }
}
