package com.example.duanlon2.controllers;

import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.services.ICourseService;
import com.example.duanlon2.models.services.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportsController {
    private final ICourseService courseService;
    private final IEnrollmentService enrollmentService;

    @GetMapping("/top_courses")
    public ResponseEntity<?> getTopCourses() {
        log.info("Fetching top courses report");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Top Courses Report Successfully")
                        .code(200)
                        .data(courseService.findTopCourses())
                        .build()
        );
    }

    @GetMapping("/teacher_courses_overview/{teacher_id}")
    public ResponseEntity<?> getTeacherCoursesOverview(@PathVariable("teacher_id") Long teacherId) {
        log.info("Fetching teacher courses overview for teacher ID: {}", teacherId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Teacher Courses Overview Successfully")
                        .code(200)
                        .data(courseService.getTeacherCoursesOverview(teacherId))
                        .build()
        );
    }

    @GetMapping("/student_progress/{student_id}")
    public ResponseEntity<?> getStudentProgress(@PathVariable("student_id") Long studentId) {
        log.info("Fetching student progress report for student ID: {}", studentId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Student Progress Report Successfully")
                        .code(200)
                        .data(enrollmentService.getStudentProgress(studentId))
                        .build()
        );
    }
}
