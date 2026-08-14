package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface INotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByNotificationIdAndUserId(Long notificationId, Long userId);
}