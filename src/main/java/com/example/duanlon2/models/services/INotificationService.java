package com.example.duanlon2.models.services;

import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.constants.UserStatus;
import com.example.duanlon2.models.dto.req.NotificationReq;
import com.example.duanlon2.models.entities.Notification;
import com.example.duanlon2.models.entities.User;

import java.util.List;

public interface INotificationService {
    Notification createNotification(NotificationReq req);
    List<Notification> findAll();
    void deleteNotification(Long id);
    Notification markAsRead(Long notificationId, User currentUser);
}
