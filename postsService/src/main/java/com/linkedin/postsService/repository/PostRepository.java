package com.linkedin.postsService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkedin.postsService.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);
    
}
