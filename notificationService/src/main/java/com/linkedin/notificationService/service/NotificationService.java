package com.linkedin.notificationService.service;

import org.springframework.stereotype.Service;

import com.linkedin.notificationService.entitiy.Notification;
import com.linkedin.notificationService.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void addNotification(Notification notification){ 
        log.info("Adding Notification for userId {}", notification.getUserId());
        notification = notificationRepository.save(notification);
        
        // SendMailer to send Email
        // FCM (Android SMS) to send SMS
    }
}
