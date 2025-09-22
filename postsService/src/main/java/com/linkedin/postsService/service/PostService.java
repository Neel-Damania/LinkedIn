package com.linkedin.postsService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.linkedin.postsService.auth.AuthContextHolder;
import com.linkedin.postsService.client.ConnectionsServiceClient;
import com.linkedin.postsService.dto.PersonDto;
import com.linkedin.postsService.dto.PostCreateRequestDto;
import com.linkedin.postsService.dto.PostDto;
import com.linkedin.postsService.entity.Post;
import com.linkedin.postsService.event.PostCreated;
import com.linkedin.postsService.exception.ResourceNotFoundException;
import com.linkedin.postsService.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    private final ConnectionsServiceClient connectionsServiceClient;

    private final KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate; 

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId) {
        log.info("Creating post for user id {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);

        List<PersonDto> personDtoList = connectionsServiceClient.getFirstDegreeConnections(userId);
        
        // Sends Notification to all the connections of the user
        for(PersonDto personDto : personDtoList) {
            PostCreated postCreated = PostCreated.builder()
                .postId(post.getId())
                .content(post.getContent())
                .userId(personDto.getUserId())
                .ownerUserId(userId)
                .build();
            postCreatedKafkaTemplate.send("post_created_topic",postCreated);
        }

        return modelMapper.map(post,PostDto.class);

    }

    public PostDto getPostById(Long postId) {
        log.info("Getting post by id {}", postId);
        
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id:" + postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPosts(Long userId) {
        log.info("Getting all posts for userId {}", userId);

        List<Post> postList = postRepository.findByUserId(userId);
        List<PostDto> postDtoList = postList.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtoList;
        
    }

}
