package com.linkedin.ConnectionsService.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.ConnectionsService.entity.Person;
import com.linkedin.ConnectionsService.service.ConnectionsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/core")
@Slf4j
@RequiredArgsConstructor
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @RequestMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId) {
        log.info("Getting first degree connections for userId: {}", userId);
        List<Person> personList = connectionsService.getFirstDegreeConnections(userId);
        return ResponseEntity.ok(personList);
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Void> sendConnectionRequest(@PathVariable Long userId) {
        log.info(
            "Sending connection request to userId: {}",userId
        );
        connectionsService.sendConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Void> acceptConnectionRequest(@PathVariable Long userId) {
        log.info(
            "Accepting connection request from userId: {}",userId
        );
        connectionsService.acceptConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Void> rejectConnectionRequest(@PathVariable Long userId) {
        log.info(
            "Rejecting connection request from userId: {}",userId
        );
        connectionsService.rejectConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

}
