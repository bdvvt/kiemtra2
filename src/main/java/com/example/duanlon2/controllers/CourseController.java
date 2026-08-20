package com.example.duanlon2.controllers;

import com.example.duanlon2.models.constants.CourseStatus;
import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.dto.req.CourseReq;
import com.example.duanlon2.models.dto.req.CourseStatusReq;
import com.example.duanlon2.models.dto.req.ReviewReq;
import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.repositories.IReviewRepository;
import com.example.duanlon2.models.services.ICourseService;
import com.example.duanlon2.models.services.IReviewService;
import jakarta.validation.Valid;
import com.example.duanlon2.models.dto.req.LessonReq;
import com.example.duanlon2.security.principal.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final ICourseService courseService;
    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> findAll(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "status",defaultValue = "") CourseStatus status,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "teacher_id", required = false) Long teacherId) {
        User currentUser = userDetails.getUser();
        log.info("Fetching courses with status: {}, search: {}", status, search);

        if (teacherId != null) {
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .message("Get Courses By Teacher Successfully")
                            .code(200)
                            .data(courseService.findByTeacherId(teacherId))
                            .build()
            );
        }

        if (search != null && !search.trim().isEmpty()) {
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .message("Get Course Successfully")
                            .code(200)
                            .data(courseService.findAllBySearch(search))
                            .build()
            );
        }
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Course Successfully")
                        .code(200)
                        .data(courseService.findAll(currentUser,status))
                        .build()
        );
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseDetail(@PathVariable Long courseId) {
        log.info("Request to get course detail for ID: {}", courseId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Course Detail Successfully")
                        .code(200)
                        .data(courseService.findByIdWithPublishedLessons(courseId))
                        .build()
        );
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<?> getPublishedLessons(@PathVariable Long courseId) {
        log.info("Request to get published lessons for course ID: {}", courseId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Published Lessons Successfully")
                        .code(200)
                        .data(courseService.getPublishedLessons(courseId))
                        .build()
        );
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<?> addLessonToCourse(@PathVariable Long courseId, @Valid @ModelAttribute LessonReq req, @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Request to add lesson to course ID: {} by user: {}", courseId, userDetails.getUser().getId());
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add Lesson Successfully")
                        .code(201)
                        .data(courseService.addLessonToCourse(courseId, req, userDetails.getUser().getId()))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> addNewCourse(@Valid @ModelAttribute CourseReq req) {
        log.info("Received request to add new course: {}", req);
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New Course Successfully")
                        .code(201)
                        .data(courseService.createCourse(req))
                        .build()
        );
    }

    @PutMapping("/{courseId}/status")
    public ResponseEntity<?> updateCourseStatus(@PathVariable Long courseId, @Valid @ModelAttribute CourseStatusReq req) {
        log.info("Request to update course status for ID: {} to {}", courseId, req.getStatus());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Update Course Status Successfully")
                        .code(200)
                        .data(courseService.updateCourseStatus(courseId, req))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @ModelAttribute CourseReq req){
        log.info("Updating course with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated Course Successfully")
                        .code(200)
                        .data(courseService.updateCourse(id, req))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropout(@PathVariable Long id){
        log.info("Deleted course with ID: {}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted Course Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping("/{course_id}/reviews")
    public ResponseEntity<?> getReviewsByCourseId(@PathVariable("course_id") Long courseId) {
        log.info("Getting reviews for course: {}", courseId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get course reviews successfully")
                        .code(200)
                        .data(reviewService.getReviewsByCourseId(courseId))
                        .build()
        );
    }

    @PostMapping("/{course_id}/reviews")
    public ResponseEntity<?> addReview(@PathVariable("course_id") Long courseId,@Valid @ModelAttribute ReviewReq req,@AuthenticationPrincipal CustomUserDetails userDetails) {

        User currentUser = userDetails.getUser();
        log.info("Student {} creating review for course {}", currentUser.getUsername(), courseId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message("Add review successfully")
                        .code(201)
                        .data(reviewService.addReview(req,courseId, currentUser))
                        .build()
        );
    }
}
