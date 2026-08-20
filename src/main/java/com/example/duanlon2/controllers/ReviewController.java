package com.example.duanlon2.controllers;

import com.example.duanlon2.models.dto.req.ReviewReq;
import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.services.IReviewService;
import com.example.duanlon2.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @PutMapping("/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable("review_id") Long reviewId, @Valid @ModelAttribute ReviewReq req, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User currentUser = customUserDetails.getUser();
        log.info("User {} updating review {}", currentUser.getUsername(), reviewId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Update review successfully")
                        .code(200)
                        .data(reviewService.updateReview(currentUser,reviewId, req))
                        .build());

    }

    @DeleteMapping("/{review_id}")
    public ResponseEntity<?> dropout(@PathVariable("review_id") Long reviewId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        User currentUser = customUserDetails.getUser();
        log.info("Deleted review with ID: {}", reviewId);
        reviewService.deleteReview(currentUser ,reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted User Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }
}
