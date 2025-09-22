package com.linkedin.userService.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String name,email,password;
}
