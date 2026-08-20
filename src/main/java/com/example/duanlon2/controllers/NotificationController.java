package com.example.duanlon2.controllers;

import com.example.duanlon2.models.dto.req.NotificationReq;
import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.services.INotificationService;
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
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final INotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> createNotification(@Valid @ModelAttribute NotificationReq req) {
        log.info("Admin creating notification: {}", req);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message("Create Notification Successfully")
                        .code(201)
                        .data(notificationService.createNotification(req))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Fetching all notification");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Notification Successfully")
                        .code(200)
                        .data(notificationService.findAll())
                        .build()
        );
    }

    @PutMapping("/{notification_id}/read")
    public ResponseEntity<?> markAsRead(
            @PathVariable("notification_id") Long notificationId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User currentUser = userDetails.getUser();
        log.info("Marking notification {} as read for user: {}", notificationId, currentUser.getUsername());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Mark notification as read successfully")
                        .code(200)
                        .data(notificationService.markAsRead(notificationId, currentUser))
                        .build()
        );
    }

    @DeleteMapping("/{notification_id}")
    public ResponseEntity<?> dropout(@PathVariable("notification_id") Long id){
        log.info("Deleted course with ID: {}", id);
        notificationService.deleteNotification(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted Notification Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }
}
