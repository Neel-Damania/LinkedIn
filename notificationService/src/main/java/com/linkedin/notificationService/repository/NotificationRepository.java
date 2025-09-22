package com.linkedin.notificationService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkedin.notificationService.entitiy.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>{ 

}
