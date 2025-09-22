package com.linkedin.ConnectionsService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.linkedin.ConnectionsService.auth.AuthContextHolder;
import com.linkedin.ConnectionsService.entity.Person;
import com.linkedin.ConnectionsService.exception.BadRequestException;
import com.linkedin.ConnectionsService.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnections(Long userId) {
        log.info("Getting first degree connections for userId: {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }
    public void sendConnectionRequest(Long receiverId) {
        log.info("Sending connection request to userId: {}", receiverId);
        Long senderId = AuthContextHolder.getCurrentUserId();
        if(senderId.equals(receiverId)){
            throw new BadRequestException("Sender and Receiver cannot be same");
        }

        boolean alreadySentRequest = personRepository.connectionRequestsExists(senderId,receiverId);
        if(alreadySentRequest){
            throw new BadRequestException("Already sent connection request");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new BadRequestException("Already connected");
        }

        personRepository.addConnectionRequest(senderId, receiverId);
        log.info("Connection Request sent Successfully to userId: {}", receiverId);

    }

    public void acceptConnectionRequest(Long receiverId) {
        log.info("Accepting connection request from userId: {}", receiverId);
        Long senderId = AuthContextHolder.getCurrentUserId();
        if(senderId.equals(receiverId)){
            throw new BadRequestException("Sender and Receiver cannot be same");
        }
        boolean alreadySentRequest = personRepository.connectionRequestsExists(senderId,receiverId);
        if(!alreadySentRequest){
            throw new BadRequestException("No connection request found");
        }
        personRepository.acceptConnectionRequest(senderId,receiverId);
        log.info("Connection Request accepted Successfully from userId: {}", receiverId);
    }

    public void rejectConnectionRequest(Long receiverId) {
        log.info("Rejecting connection request from userId: {}", receiverId);
        Long senderId = AuthContextHolder.getCurrentUserId();
        if(senderId.equals(receiverId)){
            throw new BadRequestException("Sender and Receiver cannot be same");
        }
        boolean alreadySentRequest = personRepository.connectionRequestsExists(senderId,receiverId);
        if(!alreadySentRequest){
            throw new BadRequestException("No connection request found");
        }
        personRepository.rejectConnectionRequest(senderId,receiverId);
        log.info("Connection Request rejected Successfully from userId: {}", receiverId);
    }


}
