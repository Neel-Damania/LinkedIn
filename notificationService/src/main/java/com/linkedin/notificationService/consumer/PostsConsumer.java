package com.linkedin.notificationService.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.linkedin.notificationService.entitiy.Notification;
import com.linkedin.notificationService.service.NotificationService;
import com.linkedin.postsService.event.PostCreated;
import com.linkedin.postsService.event.PostLiked;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostsConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics ="post_created_topic")
    public void handlePostCreated(PostCreated postCreated){

        log.info("Received Notification: {}",postCreated); 

        String message = String.format("Your Connection with Id: %d has created this Post: %s ",postCreated.getOwnerUserId(),postCreated.getContent());
        Notification notification = Notification.builder()
            .message(message)
            .userId(postCreated.getUserId())
            .build();

        notificationService.addNotification(notification);

    }

    @KafkaListener(topics ="post_liked_topic")
    public void handlePostLiked(PostLiked postLiked){

        log.info("Received Notification: {}",postLiked); 

        String message = String.format("User with Id: %d has Liked your Post ",postLiked.getLikedByUserId());  
        Notification notification = Notification.builder()
            .message(message)
            .userId(postLiked.getOwnerUserId())
            .build();

        notificationService.addNotification(notification);

    }

}
