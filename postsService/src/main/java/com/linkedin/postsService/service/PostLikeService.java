package com.linkedin.postsService.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.linkedin.postsService.auth.AuthContextHolder;
import com.linkedin.postsService.entity.Post;
import com.linkedin.postsService.entity.PostLike;
import com.linkedin.postsService.event.PostLiked;
import com.linkedin.postsService.exception.BadRequestException;
import com.linkedin.postsService.exception.ResourceNotFoundException;
import com.linkedin.postsService.repository.PostLikeRepository;
import com.linkedin.postsService.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final KafkaTemplate<Long,PostLiked> postLikedKafkaTemplate;
    @Transactional
    public void likePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("User with id {} liked post with id {}", userId, postId);

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id"+postId));
        
        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if (hasAlreadyLiked) {
            throw new BadRequestException("You cannot like the post again"); 
        }

        
        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);

        PostLiked postLiked = PostLiked.builder()
            .postId(postId)
            .ownerUserId(post.getUserId())
            .likedByUserId(userId)
            .build();
        
        postLikedKafkaTemplate.send("post_liked_topic",postLiked);
    
    }
    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L;
        log.info("User with id {} unliked post with id {}", userId, postId);        

        postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id"+postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if (!hasAlreadyLiked) { 
            throw new BadRequestException("You cannot unlike the post"); 
        }

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }

}
