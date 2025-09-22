package com.linkedin.ConnectionsService.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.linkedin.ConnectionsService.service.PersonService;
import com.linkedin.userService.event.UserCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceConsumer {

    private final PersonService personService;

    @KafkaListener(topics = "user_created_topic")
    public void handlePersonCreated(UserCreatedEvent userCreatedEvent) {
        log.info("Inside handlePersonCreated:{}",userCreatedEvent);
        personService.createPerson(userCreatedEvent.getUserId(),userCreatedEvent.getName());
    }
}
