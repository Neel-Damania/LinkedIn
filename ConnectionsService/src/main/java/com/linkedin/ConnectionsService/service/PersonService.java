package com.linkedin.ConnectionsService.service;

import org.springframework.stereotype.Service;

import com.linkedin.ConnectionsService.entity.Person;
import com.linkedin.ConnectionsService.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public void createPerson(Long userId, String name){
        Person person = Person.builder().name(name).userId(userId).build();

        personRepository.save(person);
    }

}
