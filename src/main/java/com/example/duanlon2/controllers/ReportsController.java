package com.example.duanlon2.controllers;

import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.services.ICourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportsController {
    private final ICourseService courseService;

    @GetMapping("/top_courses")
    @PreAuthorize("hasAuthority('ADMIN')")
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
}

