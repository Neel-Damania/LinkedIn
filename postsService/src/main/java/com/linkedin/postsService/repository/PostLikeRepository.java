package com.linkedin.postsService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkedin.postsService.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);

}
