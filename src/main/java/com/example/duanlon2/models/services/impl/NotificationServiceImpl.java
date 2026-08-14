package com.example.duanlon2.models.services.impl;

import com.example.duanlon2.exceptions.NotFoundException;
import com.example.duanlon2.models.dto.req.NotificationReq;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.Notification;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.repositories.INotificationRepository;
import com.example.duanlon2.models.repositories.IUserRepository;
import com.example.duanlon2.models.services.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final INotificationRepository notificationRepository;
    private final IUserRepository userRepository;

    @Override
    public Notification createNotification(NotificationReq req) {
        log.info("Creating notification for user ID: {}", req.getUserId());
        User targetUser = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng với ID: " + req.getUserId()));
        Notification notification = Notification.builder()
                .user(targetUser)
                .message(req.getMessage())
                .type(req.getType())
                .targetUrl(req.getTargetUrl())
                .isRead(false)
                .build();

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findAll() {
        log.info("Fetching all notifications");
        return notificationRepository.findAll();
    }

    @Override
    public Notification markAsRead(Long notificationId, User currentUser) {
        log.info("User {} marking notification {} as read", currentUser.getUsername(), notificationId);
        Notification notification = notificationRepository.findByNotificationIdAndUserId(notificationId, currentUser.getId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông báo hoặc bạn không có quyền cập nhật thông báo này"));
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        log.info("Deleting Notification record with ID: {}", id);
        Notification deleteNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + id));
        notificationRepository.delete(deleteNotification);
    }
}
