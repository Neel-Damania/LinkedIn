package com.linkedin.userService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.userService.dto.LoginDto;
import com.linkedin.userService.dto.SignupRequestDto;
import com.linkedin.userService.dto.UserDto;
import com.linkedin.userService.service.Authservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final Authservice authService;
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        UserDto userDto = authService.signup(signupRequestDto);
        return new ResponseEntity<>(userDto,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) { 
        String token = authService.login(loginDto);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

}
