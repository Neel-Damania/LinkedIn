package com.linkedin.postsService.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostDto {

    private Long id;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;

}
