package com.linkedin.userService.service;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.linkedin.userService.Entity.User;
import com.linkedin.userService.dto.LoginDto;
import com.linkedin.userService.dto.SignupRequestDto;
import com.linkedin.userService.dto.UserDto;
import com.linkedin.userService.event.UserCreatedEvent;
import com.linkedin.userService.exception.BadRequestException;
import com.linkedin.userService.repository.UserRepository;
import com.linkedin.userService.utils.BCrypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class Authservice {

    public final UserRepository userRepository;
    
    public final ModelMapper modelMapper;

    public final JwtService jwtService;

    public final KafkaTemplate<Long,UserCreatedEvent> userCreatedEventKafkaTemplate;

    public UserDto signup(SignupRequestDto signupRequestDto) {
        log.info("Signup request received for user {}", signupRequestDto.getEmail());

        boolean userExists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(userExists) {
            throw new BadRequestException("User with email "+signupRequestDto.getEmail()+" already exists");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(BCrypt.hash(signupRequestDto.getPassword()));
        user = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder().userId(user.getId()).name(user.getName()).build();

        userCreatedEventKafkaTemplate.send("user_created_topic",userCreatedEvent);


        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginDto loginDto) {
        log.info("Login request received for user {}", loginDto.getEmail());

        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(()-> new BadRequestException("Invalid Credentials"));
        boolean isPasswordCorrect = BCrypt.match(loginDto.getPassword(), user.getPassword());
        if(!isPasswordCorrect) {
            throw new BadRequestException("Invalid Credentials");
        }

        String token = jwtService.generateAccessToken(user);
        return token;
    }

}
